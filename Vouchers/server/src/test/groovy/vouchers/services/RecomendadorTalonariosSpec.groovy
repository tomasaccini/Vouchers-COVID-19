package vouchers.services

import enums.ProductoTipo
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import vouchers.*

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RecomendadorTalonariosSpec extends Specification {

    @Autowired
    RecomendadorTalonarios recomendadorTalonarios
    ClienteService clienteService
    VoucherService voucherService

    private static Boolean setupHecho = false
    static Long clienteId
    static Long negocioId
    static InformacionVoucher iv

    def setup() {
        if (setupHecho) return

        Negocio negocio = new Negocio()
        negocio.nombre = "Blue Dog"
        negocio.email = "sales@bluedog.com"
        negocio.contrasenia = "1234"
        negocio.cuentaVerificada = true
        negocio.numeroTelefonico = "1234"
        negocio.categoria = "Cervezería"
        Direccion nuevaDireccion = new Direccion()
        nuevaDireccion.calle = "calle falsa"
        nuevaDireccion.numero = "123"
        nuevaDireccion.departamento = "11D"
        nuevaDireccion.provincia = "Buenos Aires"
        nuevaDireccion.pais = "Argentina"

        negocio.direccion = nuevaDireccion
        negocio.website = "bluedog.com"
        negocio.save(flush: true, failOnError: true)

        Cliente cliente = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        cliente.cuentaVerificada = true
        cliente.save(flush: true, failOnError: true)

        setupHecho = true
        clienteId = cliente.id
        negocioId = negocio.id
        Voucher.findAll().each { it.delete(flush:true, failOnError:true) }
        Talonario.findAll().each { it.delete(flush:true, failOnError:true) }
    }

    private Long crearTalonario() {
        Negocio negocio = Negocio.findById(negocioId)
        Producto producto = new Producto()
        producto.descripcion = "Hamburguesa con cebolla, cheddar, huevo, jamón, todo."
        producto.nombre = "Hamburguesa Blue Dog"
        producto.tipo = ProductoTipo.COMIDA_RAPIDA
        negocio.addToProductos(producto)
        negocio.save(flush: true, failOnError: true)

        Item item = new Item(producto: producto, cantidad: 1)
        iv = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: new Date('2022/08/15'))
        iv.addToItems(item)

        Talonario talonario = new Talonario(informacionVoucher: iv, stock: 10, activo: true)
        negocio.addToTalonarios(talonario)
        negocio.save(flush: true, failOnError: true)
        return talonario.id
    }

    def cleanup() {
    }

    void "setup test"() {
        expect:
        Negocio.count() == 3
        Producto.count() == 2
        Talonario.count() == 0
    }

    void "recomendador sin talonarios devuelve lista vacia"() {
        List<Talonario> talonarios = recomendadorTalonarios.generarOrdenDeRecomendacion()
        expect: "la lista esta vacia"
        talonarios.size() == 0
    }

    void "recomendador con un talonario sin ventas devuelve ese talonario"() {
        Long talonarioId = crearTalonario()
        List<Talonario> talonarios = recomendadorTalonarios.generarOrdenDeRecomendacion()
        expect: "la lista tiene el unico talonario"
        talonarios.size() == 1
        talonarios[0].id == talonarioId
    }

    void "recomendador con un talonario con ventas devuelve ese talonario"() {
        Long talonarioId = crearTalonario()
        clienteService.comprarVoucher(clienteId, talonarioId)
        List<Talonario> talonarios = recomendadorTalonarios.generarOrdenDeRecomendacion()
        expect: "la lista tiene el unico talonario"
        talonarios.size() == 1
        talonarios[0].id == talonarioId
    }

    void "recomendador con un talonario con ventas y rating devuelve ese talonario"() {
        Long talonarioId = crearTalonario()
        Voucher voucher = clienteService.comprarVoucher(clienteId, talonarioId)
        voucherService.cambiarRating(voucher.id, 5 as Short)
        List<Talonario> talonarios = recomendadorTalonarios.generarOrdenDeRecomendacion()
        expect: "la lista tiene el unico talonario"
        talonarios.size() == 1
        talonarios[0].id == talonarioId
    }

    void "recomendador con dos talonarios, uno con ventas y el otro sin"() {
        Long talonarioId1 = crearTalonario()
        Long talonarioId2 = crearTalonario()
        clienteService.comprarVoucher(clienteId, talonarioId1)
        List<Talonario> talonarios = recomendadorTalonarios.generarOrdenDeRecomendacion()
        expect: "la lista tiene dos talonarios"
        talonarios.size() == 2
        talonarios[0].id == talonarioId1
        talonarios[1].id == talonarioId2
    }

    void "recomendador con dos talonarios, uno con mas ventas que el otro"() {
        Long talonarioId1 = crearTalonario()
        Long talonarioId2 = crearTalonario()
        clienteService.comprarVoucher(clienteId, talonarioId1)
        clienteService.comprarVoucher(clienteId, talonarioId1)
        clienteService.comprarVoucher(clienteId, talonarioId2)
        List<Talonario> talonarios = recomendadorTalonarios.generarOrdenDeRecomendacion()
        expect: "la lista tiene dos talonarios"
        talonarios.size() == 2
        talonarios[0].id == talonarioId1
        talonarios[1].id == talonarioId2
    }

    void "recomendador con dos talonarios, misma cantidad de ventas, distinto rating"() {
        Long talonarioId1 = crearTalonario()
        Long talonarioId2 = crearTalonario()
        Voucher voucher1 = clienteService.comprarVoucher(clienteId, talonarioId1)
        Voucher voucher2 = clienteService.comprarVoucher(clienteId, talonarioId1)
        Voucher voucher3 = clienteService.comprarVoucher(clienteId, talonarioId2)
        Voucher voucher4 = clienteService.comprarVoucher(clienteId, talonarioId2)
        voucherService.cambiarRating(voucher1.id, 1 as Short)
        voucherService.cambiarRating(voucher2.id, 1 as Short)
        voucherService.cambiarRating(voucher3.id, 5 as Short)
        voucherService.cambiarRating(voucher4.id, 5 as Short)
        List<Talonario> talonarios = recomendadorTalonarios.generarOrdenDeRecomendacion()
        expect: "la lista tiene dos talonarios"
        talonarios.size() == 2
        talonarios[0].id == talonarioId2
        talonarios[1].id == talonarioId1
    }
}
