package vouchers

import enums.ProductoTipo
import grails.gorm.transactions.Transactional

import java.text.SimpleDateFormat

@Transactional
class TalonarioService {

    RecomendadorTalonarios recomendadorTalonarios

    List<Talonario> generarOrdenDeRecomendacion() {
        return recomendadorTalonarios.generarOrdenDeRecomendacion()
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

    Integer count() {
        return Talonario.findAll().size()
    }

    Talonario get(Long talonarioId) {
        return Talonario.findById(talonarioId)
    }

    List<Talonario> list(Map args) {
        Talonario.list(args)
    }

    Voucher comprarVoucher(talonarioId, clienteId) {
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
