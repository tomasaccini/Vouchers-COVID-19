package vouchers

import enums.ProductoTipo
import grails.gorm.transactions.Transactional

import java.text.SimpleDateFormat

@Transactional
class TalonarioService {

    List<Talonario> talonarioDB = []

    Talonario createMock(String name) {
        InformacionVoucher iv = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: new Date('2020/08/15'))
        Talonario talonario = new Talonario(informacionVoucher: iv, stock: 5)
        talonario.negocio = mockBusiness(name)
        talonarioDB.add(talonario)
        return talonario
    }


    Talonario crear(String negocioId, Integer stock, Double precio, String descripcion, String validoDesdeStr, String validoHastaStr) {
        Negocio negocio = Negocio.findById(negocioId)
        if (negocio == null) {
            throw new RuntimeException('No se puede crear un talonario para un voucher invalido')
        }
        if (stock < 0) {
            throw new RuntimeException('No se puede crear un talonario con stock negativo')
        }
        if (precio < 0) {
            throw new RuntimeException('No se puede crear un talonario con precio negativo')
        }
        if (descripcion.isEmpty()) {
            throw new RuntimeException('No se puede crear un talonario con descripcion vacia')
        }
        if (negocio.tieneTalonarioConDescripcion(descripcion)) {
            throw new RuntimeException('No se puede crear un talonario con descripcion ya utilizada')
        }

        def pattern = "yyyy-MM-dd"

        Date validoDesde = new SimpleDateFormat(pattern).parse(validoDesdeStr)
        Date validoHasta = new SimpleDateFormat(pattern).parse(validoHastaStr)

        InformacionVoucher vi = new InformacionVoucher(precio: precio, descripcion: descripcion, validoDesde: validoDesde, validoHasta: validoHasta).save(failOnError: true)
        Talonario talonario = new Talonario(informacionVoucher: vi, stock: stock, negocio: negocio)
        talonario.save(failOnError: true)

        talonario
    }

    Talonario agregarProductos(Talonario talonario, def productos) {
        println("Agregando productos")
        println(productos)
        for (p in productos) {
            Item item = new Item(
                    cantidad: p["cantidad"],
                    producto: Producto.get(p["id"])
            ).save(failOnError: true)
            talonario.informacionVoucher.addToItems(item)
        }
        talonario.save()

        talonario
    }

    Talonario pausar(Long negocioId, Long talonarioId){
        Talonario talonario = Talonario.get(talonarioId)
        if (talonario.negocio.id != negocioId){
            throw new Exception('El talonario no pertenece al negocio indicado')
        }
        talonario.activo = false
        talonario.save()
        talonario
    }

    Talonario activar(Long negocioId, Long talonarioId){
        Talonario talonario = Talonario.get(talonarioId)
        if (talonario.negocio.id != negocioId){
            throw new Exception('El talonario no pertenece al negocio indicado')
        }
        talonario.activo = true
        talonario.save(failOnError: true, flush: true)
        talonario
    }

    List<Talonario> getAll() {
        return Talonario.findAll()
    }

    Integer count() {
        return Talonario.findAll().size()
    }

    // !!!!
    private Negocio mockBusiness(name) {
        Negocio business = new Negocio()
        business.nombre = name
        business.email = "sales@bluedog.com"
        business.contrasenia = "1234"
        business.cuentaVerificada = true
        business.numeroTelefonico = "1234"
        business.categoria = "Cervezería"
        Direccion newAddress = new Direccion()
        newAddress.calle = "calle falsa"
        newAddress.numero = "123"
        newAddress.departamento = "11D"
        newAddress.provincia = "Buenos Aires"
        newAddress.pais = "Argentina"

        business.direccion = newAddress
        business.website = "bluedog.com"

        Producto product = new Producto()
        product.descripcion = "Hamburguesa con cebolla, cheddar, huevo, jamón, todo."
        product.nombre = "Hamburguesa Blue Dog"
        product.tipo = ProductoTipo.COMIDA_RAPIDA
        business.addToProducts(product)

        Item item = new Item(producto: product, cantidad: 1)
        InformacionVoucher vi = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: new Date('2020/08/15'))
        vi.addToItems(item)

        return business
    }

    Talonario get(Long talonarioId) {
        return Talonario.findById(talonarioId)
    }

    List<Talonario> list(Map args) {
        Talonario.list(args)
    }

    Voucher comprarVoucher(talonarioId, clienteId) {
        // TODO pasar a un service el get y todo en realidad !!!!!
        Cliente cliente = Cliente.get(clienteId)
        println(cliente)
        println(Cliente.getAll())
        Talonario talonario = Talonario.get(talonarioId)

        if (cliente == null) {
            throw new RuntimeException("El cliente " + clienteId + " no existe")
        }

        if (talonario == null) {
            throw new RuntimeException("El talonario " + talonarioId + " no existe")
        }

        return talonario.comprarVoucher(cliente)
    }

    List<Talonario> findSimilar(String q, Map map) {
        String query = "select distinct(t) from Talonario as t "
        query += "join t.informacionVoucher.items as items"
        query += " where lower(t.informacionVoucher.descripcion) like :search "
        query += " or lower(items.producto.descripcion) like :search "
        query += " or lower(items.producto.nombre) like :search "
        Talonario.executeQuery(query, [search: "%${q}%".toLowerCase()], map)
    }
}
