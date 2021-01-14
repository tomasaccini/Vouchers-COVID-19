package vouchers.services

import enums.ProductoTipo
import enums.states.VoucherEstado
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import vouchers.*

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class NegocioServiceSpec extends Specification{

    @Autowired
    NegocioService negocioService
    ClienteService clienteService

    private static Boolean setupHecho = false
    static Long clienteId
    static Long talonarioId
    static Long negocioId
    static Long itemId

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
        InformacionVoucher iv = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta:  new Date('2022/08/15'))
        iv.addToItems(item)
        Talonario talonario = new Talonario(informacionVoucher: iv, stock: 3, activo: true)
        negocio.addToTalonarios(talonario)
        negocio.save(flush: true, failOnError: true)

        Cliente cliente = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        cliente.cuentaVerificada = true
        cliente.save(flush:true, failOnError:true)

        setupHecho = true
        clienteId = cliente.id
        talonarioId = talonario.id
        negocioId = negocio.id
        itemId = item.id
    }

    void "setup test"() {
        expect:
        Negocio.count() == 3
        Producto.count() == 3
        Talonario.count() == 3
    }

    void "confirmar canjeo de voucher"() {
        Voucher v = clienteService.comprarVoucher(clienteId, talonarioId)
        Cliente cliente = Cliente.get(clienteId)
        clienteService.retirarVoucher(clienteId, v)
        negocioService.confirmarCanjeVoucher(negocioId, v)
        expect:"El estado del voucher es Canjeado"
        v != null && cliente.getVouchers().size() == 1 && cliente.getVouchers()[0] == v && v.getEstado() == VoucherEstado.Canjeado
    }

    void "confirmar canjeo de voucher antes que el cliente lo retire"() {
        Voucher v = clienteService.comprarVoucher(clienteId, talonarioId)
        when:
        negocioService.confirmarCanjeVoucher(negocioId, v)
        then: "Lanza error"
        thrown RuntimeException
        v.estado == VoucherEstado.Comprado
    }

    void "confirmar canjeo de voucher de otro negocio"() {
        Negocio negocio2 = new Negocio(nombre: "Mc", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Corrientes", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "mc@gmail.com", contrasenia: "mc1234")
        negocio2.save()
        Voucher v = clienteService.comprarVoucher(clienteId, talonarioId)
        clienteService.retirarVoucher(clienteId, v)
        when:
        negocioService.confirmarCanjeVoucher(negocio2.id, v)
        then: "Throw error"
        thrown RuntimeException
        v.estado == VoucherEstado.ConfirmacionPendiente
    }
}
