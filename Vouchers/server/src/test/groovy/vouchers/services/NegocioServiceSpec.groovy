package vouchers.services

import enums.ProductType
import enums.states.VoucherState
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import vouchers.Direccion
import vouchers.Negocio
import vouchers.NegocioService
import vouchers.Cliente
import vouchers.ClienteService
import vouchers.Tarifario

import vouchers.Item
import vouchers.Producto
import vouchers.Voucher
import vouchers.InformacionVoucher

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class NegocioServiceSpec extends Specification{

    @Autowired
    NegocioService businessService
    ClienteService clientService

    private static Boolean setupIsDone = false
    static Long businessId
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
        Tarifario counterfoil = new Tarifario(informacionVoucher: vi, stock: 3, isActive: true)
        business.addToTarifarios(counterfoil)
        business.save(flush: true, failOnError: true)

        Cliente client = new Cliente(fullName: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        client.cuentaVerificada = true
        client.save(flush:true, failOnError:true)

        setupIsDone = true
        clientId = client.id
        businessId = business.id
    }

    void "setup test"() {
        expect:
        Negocio.count() == 1
        Producto.count() == 1
        Tarifario.count() == 1
    }

    void "confirm voucher retirement"() {
        Tarifario counterfoil = Tarifario.findById(1)
        Voucher v = clientService.comprarVoucher(clientId, counterfoil)
        Cliente client = Cliente.get(clientId)
        clientService.retirarVoucher(clientId, v)
        businessService.confirmRetireVoucher(businessId, v)
        expect:"Vouchers status in retired"
        v != null && client.getVouchers().size() == 1 && client.getVouchers()[0] == v && v.getState() == VoucherState.Retirado
    }

    void "confirm voucher retirement before client retires it"() {
        Tarifario counterfoil = Tarifario.findById(1)
        Voucher v = clientService.comprarVoucher(clientId, counterfoil)
        when:
        businessService.confirmRetireVoucher(businessId, v)
        then: "Throw error"
        thrown RuntimeException
        v.state == VoucherState.Comprado
    }

    void "confirm voucher from other business"() {
        Negocio b2 = new Negocio(nombre: "Mc", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Corrientes", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "mc@gmail.com", contrasenia: "mc1234")
        b2.save()
        Tarifario counterfoil = Tarifario.findById(1)
        Voucher v = clientService.comprarVoucher(clientId, counterfoil)
        Cliente client = Cliente.get(clientId)
        clientService.retirarVoucher(clientId, v)
        when:
        businessService.confirmRetireVoucher(b2.id, v)
        then: "Throw error"
        thrown RuntimeException
        v.state == VoucherState.ConfirmacionPendiente
    }
}
