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
class TalonarioServiceSpec extends Specification {

    @Autowired
    NegocioService negocioService
    ClienteService clienteService
    TalonarioService talonarioService

    private static Boolean setupHecho = false
    static Long negocioId
    static Talonario talonario_activo
    static Talonario talonario_inactivo
    static Talonario talonario_sin_stock
    static InformacionVoucher iv
    static Long clienteId

    def setup() {
        if (setupHecho) return

        Negocio negocio = new Negocio()
        negocio.nombre = "Blue Dog"
        negocio.email = "sales@bluedog.com"
        negocio.contrasenia = "1234"
        negocio.cuentaVerificada = true
        negocio.numeroTelefonico = "1234"
        negocio.categoria = "Cervezería"
        Direccion direccion = new Direccion()
        direccion.calle = "calle falsa"
        direccion.numero = "123"
        direccion.departamento = "11D"
        direccion.provincia = "Buenos Aires"
        direccion.pais = "Argentina"

        negocio.direccion = direccion
        negocio.website = "bluedog.com"

        Producto producto = new Producto()
        producto.descripcion = "Hamburguesa con cebolla, cheddar, huevo, jamón, todo."
        producto.nombre = "Hamburguesa Blue Dog"
        producto.tipo = ProductoTipo.COMIDA_RAPIDA
        negocio.addToProductos(producto)
        negocio.save(flush: true, failOnError: true)

        Item item = new Item(producto: producto, cantidad: 1)
        iv = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: new Date('2022/08/15'))
        iv.addToItems(item)
        talonario_activo = new Talonario(informacionVoucher: iv, stock: 3, activo: true)
        negocio.addToTalonarios(talonario_activo)
        talonario_inactivo = new Talonario(informacionVoucher: iv, stock: 10, activo: false)
        negocio.addToTalonarios(talonario_inactivo)
        talonario_sin_stock = new Talonario(informacionVoucher: iv, stock: 0, activo: true)
        negocio.addToTalonarios(talonario_sin_stock)

        negocio.save(flush: true, failOnError: true)

        Cliente client = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        client.cuentaVerificada = true
        client.save(flush: true, failOnError: true)

        setupHecho = true
        clienteId = client.id
        negocioId = negocio.id
    }

    def cleanup() {
    }

    void "setup test"() {
        expect:
        Negocio.count() == 3
        Producto.count() == 3
        Talonario.count() == 5
    }

    void "constructor"() {
        expect: "talonarios construidos correctamente"
        talonario_activo != null && talonario_activo.stock == 3 && talonario_activo.informacionVoucher == iv && talonario_activo.activo
        talonario_inactivo != null && talonario_inactivo.stock == 10 && talonario_inactivo.informacionVoucher == iv && !talonario_inactivo.activo
        talonario_sin_stock != null && talonario_sin_stock.stock == 0 && talonario_sin_stock.informacionVoucher == iv && talonario_sin_stock.activo
    }

    void "comprar vouchers"() {
        Voucher v = clienteService.comprarVoucher(clienteId, talonario_activo.id)
        talonario_activo = Talonario.findById(talonario_activo.id)
        expect: "Voucher comprado correctamente"
        v != null && talonario_activo.stock == 2 && talonario_activo.informacionVoucher.id == iv.id && talonario_activo.getVouchers().size() == 1 && talonario_activo.getVouchers()[0] == v && talonario_activo.activo
    }

    void "comprar voucher sin stock"() {
        when:
        clienteService.comprarVoucher(clienteId, talonario_sin_stock.id)
        then: "Throw error"
        thrown RuntimeException
    }

    void "activar talonario"() {
        talonarioService.activar(talonario_inactivo.id)
        talonario_inactivo = Talonario.findById(talonario_inactivo.id)
        expect: "Talonario esta ahora activo"
        talonario_inactivo.activo
    }

    void "activar y desactivar talonario"() {
        talonarioService.activar(talonario_inactivo.id)
        talonarioService.desactivar(talonario_inactivo.id)
        talonario_inactivo = Talonario.findById(talonario_inactivo.id)
        expect: "Talonario esta ahora inactivo"
        !talonario_inactivo.activo
    }
}
