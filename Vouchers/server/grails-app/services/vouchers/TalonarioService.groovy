package vouchers

import enums.ProductType
import grails.gorm.transactions.Transactional

@Transactional
class TalonarioService {

    VoucherService voucherService

    // !!!!
    List<Talonario> counterfoilDB = []

    // !!!!
    TalonarioService() {
        /*
        counterfoilDB = [
                createMock("Restaurant 1"),
                createMock("Restaurant 2"),
                createMock("Restaurant 3"),
                createMock("Restaurant 4"),
                createMock("Restaurant 5"),
        ]
         */
    }

    // !!!!
    Talonario createMock(String name) {
        InformacionVoucher vi = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta:  new Date('2020/08/15'))
        Talonario counterfoil = new Talonario(informacionVoucher: vi, stock: 5)
        counterfoil.negocio = mockBusiness(name)
        counterfoilDB.add(counterfoil)
        return counterfoil
    }

    Talonario createMock(String negocioId, Integer stock, Double precio, String descripcion, String validoDesdeStr, String validoHastaStr) {
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
        Date validoDesde = new Date(validoDesdeStr)
        Date validoHasta = new Date(validoHastaStr)

        println("!!!!" + validoDesde)

        InformacionVoucher vi = new InformacionVoucher(precio: precio, descripcion: descripcion, validoDesde: validoDesde, validoHasta:  validoHasta)
        Talonario talonario = new Talonario(informacionVoucher: vi, stock: stock, negocio: negocio)
        talonario.save()

        talonario
    }

    List<Talonario> getAll() {
        return Talonario.findAll()
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
        product.type = ProductType.FAST_FOOD
        business.addToProducts(product)

        Item item = new Item(producto: product, cantidad: 1)
        InformacionVoucher vi = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta:  new Date('2020/08/15'))
        vi.addToItems(item)
        // TODO business counterfoils will be recursive
        // Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 3, isActive: true)
        // business.addToCounterfoils(counterfoil)

        return business
    }

    Talonario get(Long counterfoilId) {
        return Talonario.findById(counterfoilId)
    }

    List<Talonario> list(Map args) {
        Talonario.list(args)
    }

    /*
    * Activates counterfoil
    * If already active, nothing happens
    */
    def activate(Long id) {
        Talonario counterfoil = Talonario.get(id)
        if (counterfoil.activo) {
            return
        }
        counterfoil.activo = true
        counterfoil.save(flush:true)
    }

    /*
    * Deactivates counterfoil
    * If already not active, nothing happens
    */
    def deactivate(Long id) {
        Talonario counterfoil = Talonario.get(id)
        if (!counterfoil.activo) {
            return
        }
        counterfoil.activo = false
        counterfoil.save(flush:true)
    }

    List<Talonario> findSimilar(String q, Map map) {
        String query = "select distinct(t) from Talonario as t "
        query += "join t.informacionVoucher.items as items"
        query += " where lower(t.informacionVoucher.descripcion) like :search "
        query += " or lower(items.producto.descripcion) like :search "
        query += " or lower(items.producto.nombre) like :search "
        Talonario.executeQuery(query, [search: "%${q}%".toLowerCase()] , map)
    }

}
