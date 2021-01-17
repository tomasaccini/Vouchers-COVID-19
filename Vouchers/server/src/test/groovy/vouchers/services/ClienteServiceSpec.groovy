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
class ClienteServiceSpec extends Specification {

    @Autowired
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
        Direccion nuevaDireccion = new Direccion()
        nuevaDireccion.calle = "calle falsa"
        nuevaDireccion.numero = "123"
        nuevaDireccion.departamento = "11D"
        nuevaDireccion.provincia = "Buenos Aires"
        nuevaDireccion.pais = "Argentina"

        negocio.direccion = nuevaDireccion
        negocio.website = "bluedog.com"

        Producto producto = new Producto()
        producto.descripcion = "Hamburguesa con cebolla, cheddar, huevo, jamón, todo."
        producto.nombre = "Hamburguesa Blue Dog"
        producto.tipo = ProductoTipo.COMIDA_RAPIDA
        negocio.addToProductos(producto)
        negocio.save(flush: true, failOnError: true)

        Item item = new Item(producto: producto, cantidad: 1)
        InformacionVoucher iv = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: new Date('2022/08/15'))
        iv.addToItems(item)
        Talonario talonario = new Talonario(informacionVoucher: iv, stock: 3, activo: true)
        negocio.addToTalonarios(talonario)
        negocio.save(flush: true, failOnError: true)

        Cliente cliente = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        cliente.cuentaVerificada = true
        cliente.save(flush: true, failOnError: true)

        setupHecho = true
        clienteId = cliente.id
        talonarioId = talonario.id
        negocioId = negocio.id
        itemId = item.id
    }

    def cleanup() {
    }

    void "setup test"() {
        expect:
        Negocio.count() == 3
        Producto.count() == 3
        Talonario.count() == 3
    }

    void "comprar un voucher"() {
        Talonario talonario = Talonario.findById(talonarioId)

        Voucher v = clienteService.comprarVoucher(clienteId, talonarioId)
        Cliente cliente = Cliente.get(clienteId)
        expect: "Voucher comprado correctamente"
        v?.id != null
        cliente.vouchers.size() == 1
        talonario.vouchers.size() == 1
        cliente.getVoucher(v.id) == v
    }

    void "comprar dos vouchers"() {
        Talonario talonario = Talonario.findById(talonarioId)

        Voucher v1 = clienteService.comprarVoucher(clienteId, talonarioId)
        Voucher v2 = clienteService.comprarVoucher(clienteId, talonarioId)
        Cliente cliente = Cliente.get(clienteId)
        expect: "Vouchers comprados correctamente"
        v1?.id != null
        v2?.id != null
        cliente.vouchers.size() == 2
        talonario.vouchers.size() == 2
        cliente.getVoucher(v1.id) == v1
        cliente.getVoucher(v2.id) == v2
    }

    void "retirar Voucher"() {
        Voucher v = clienteService.comprarVoucher(clienteId, talonarioId)
        Cliente cliente = Cliente.get(clienteId)
        clienteService.retirarVoucher(clienteId, v)
        expect: "El estado del voucher es Confirmacion Pendiente"
        v != null && cliente.getVouchers().size() == 1 && cliente.getVouchers()[0] == v && v.getEstado() == VoucherEstado.ConfirmacionPendiente && cliente.getVoucher(v.id) == v
    }

    void "comprar voucher expirado"() {
        Negocio n = Negocio.findById(negocioId)
        InformacionVoucher iv = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2019/01/01'), validoHasta: new Date('2020/01/01'))
        iv.addToItems(Item.findById(itemId))
        Talonario talonario = new Talonario(informacionVoucher: iv, stock: 3, activo: true)
        n.addToTalonarios(talonario)
        n.save(flush: true, failOnError: true)
        when:
        clienteService.comprarVoucher(clienteId, talonario.id)
        then: "Throw error"
        thrown RuntimeException
    }

    void "retirar voucher de otro cliente"() {
        Cliente c1 = Cliente.get(clienteId)
        Cliente c2 = new Cliente(nombreCompleto: "Mariano Iudica", email: "iudica@gmail.com", contrasenia: "iudica1234")
        c2.cuentaVerificada = true
        c2.save(flush: true, failOnError: true)
        Voucher v = clienteService.comprarVoucher(c1.id, talonarioId)
        when:
        clienteService.retirarVoucher(c2.id, v)
        then: "Throw error"
        thrown RuntimeException
        v.estado == VoucherEstado.Comprado
    }
}
