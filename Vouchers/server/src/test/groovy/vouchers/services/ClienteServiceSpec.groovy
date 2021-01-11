package vouchers.services

import enums.ProductType
import enums.states.VoucherEstado
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import vouchers.Direccion
import vouchers.Negocio
import vouchers.Cliente
import vouchers.ClienteService
import vouchers.Talonario

import vouchers.Item
import vouchers.Producto
import vouchers.Voucher
import vouchers.InformacionVoucher

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ClienteServiceSpec extends Specification{

    @Autowired
    ClienteService clientService

    private static Boolean setupIsDone = false
    static Long clientId

    def setup() {
        if (setupIsDone) return

        Negocio business = new Negocio()
        business.nombre = "Blue Dog"
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
        business.save(flush: true, failOnError: true)

        Item item = new Item(producto: product, cantidad: 1)
        InformacionVoucher vi = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta:  new Date('2020/08/15'))
        vi.addToItems(item)
        Talonario counterfoil = new Talonario(informacionVoucher: vi, stock: 3, isActive: true)
        business.addToTalonarios(counterfoil)
        business.save(flush: true, failOnError: true)

        Cliente client = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        client.cuentaVerificada = true
        client.save(flush:true, failOnError:true)

        setupIsDone = true
        clientId = client.id
    }

    def cleanup() {
    }

    void "setup test"() {
        expect:
        Negocio.count() == 1
        Producto.count() == 1
        Talonario.count() == 1
    }

    void "buy one voucher"() {
        Talonario counterfoil = Talonario.findById(1)

        Voucher v = clientService.comprarVoucher(clientId, counterfoil)
        Cliente client = Cliente.get(clientId)
        expect:"Voucher bought correctly"
        v?.id != null
        client.vouchers.size() == 1
        counterfoil.vouchers.size() == 1
    }

    void "buy two vouchers"() {
        Talonario counterfoil = Talonario.findById(1)

        Voucher v1 = clientService.comprarVoucher(clientId, counterfoil)
        Voucher v2 = clientService.comprarVoucher(clientId, counterfoil)
        Cliente client = Cliente.get(clientId)
        expect:"Voucher bought correctly"
        v1?.id != null
        v2?.id != null
        client.vouchers.size() == 2
        counterfoil.vouchers.size() == 2
    }

    void "retire Voucher"() {
        Talonario counterfoil = Talonario.findById(1)

        Voucher v = clientService.comprarVoucher(clientId, counterfoil)
        Cliente client = Cliente.get(clientId)
        clientService.retirarVoucher(clientId, v)
        expect:"Vouchers status in pending retire"
        v != null && client.getVouchers().size() == 1 && client.getVouchers()[0] == v && v.getEstado() == VoucherEstado.ConfirmacionPendiente
    }

    void "test expiration of voucher"() {
        Negocio b = Negocio.findById(1)
        InformacionVoucher vi = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2019/01/01'), validoHasta:  new Date('2020/01/01'))
        vi.addToItems(Item.findById(1))
        Talonario counterfoil = new Talonario(informacionVoucher: vi, stock: 3, isActive: true)
        b.addToTalonarios(counterfoil)
        b.save(flush: true, failOnError: true)
        Voucher v = clientService.comprarVoucher(clientId, counterfoil)
        when:
        clientService.retirarVoucher(clientId, v)
        then: "Throw error"
        thrown RuntimeException
        v.estado == VoucherEstado.Expirado
    }

    void "test retire voucher from other client"() {
        Cliente c1 = Cliente.get(clientId)
        Cliente c2 = new Cliente(nombreCompleto: "Mariano Iudica", email: "iudica@gmail.com", contrasenia: "iudica1234")
        c2.cuentaVerificada = true
        c2.save(flush:true, failOnError:true)
        Talonario counterfoil = Talonario.findById(1)
        Voucher v = clientService.comprarVoucher(c1.id, counterfoil)
        when:
        clientService.retirarVoucher(c2.id, v)
        then: "Throw error"
        thrown RuntimeException
        v.estado == VoucherEstado.Comprado
    }
}
