package vouchers.services

import enums.ProductType
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
import vouchers.Talonario
import vouchers.TalonarioService

import vouchers.Item
import vouchers.Producto
import vouchers.Voucher
import vouchers.InformacionVoucher

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TalonarioServiceSpec extends Specification{

    @Autowired
    NegocioService businessService
    ClienteService clientService
    TalonarioService counterfoilService

    private static Boolean setupIsDone = false
    static Long businessId
    static Talonario counterfoil_active
    static Talonario counterfoil_inactive
    static Talonario counterfoil_no_stock
    static InformacionVoucher vi
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
        vi = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta:  new Date('2020/08/15'))
        vi.addToItems(item)
        counterfoil_active = new Talonario(informacionVoucher: vi, stock: 3, activo: true)
        business.addToTalonarios(counterfoil_active)
        counterfoil_inactive = new Talonario(informacionVoucher: vi, stock: 10, activo: false)
        business.addToTalonarios(counterfoil_inactive)
        counterfoil_no_stock = new Talonario(informacionVoucher: vi, stock: 0, activo: true)
        business.addToTalonarios(counterfoil_no_stock)

        business.save(flush: true, failOnError: true)

        Cliente client = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        client.cuentaVerificada = true
        client.save(flush:true, failOnError:true)

        setupIsDone = true
        clientId = client.id
        businessId = business.id
    }

    def cleanup() {
    }

    void "setup test"() {
        expect:
        Negocio.count() == 1
        Producto.count() == 1
        Talonario.count() == 3
    }

    void "constructor"() {
        expect:"counterfoils constructed correctly"
        counterfoil_active != null && counterfoil_active.stock == 3 && counterfoil_active.informacionVoucher == vi && counterfoil_active.activo
        counterfoil_inactive != null && counterfoil_inactive.stock == 10 && counterfoil_inactive.informacionVoucher == vi && !counterfoil_inactive.activo
        counterfoil_no_stock != null && counterfoil_no_stock.stock == 0 && counterfoil_no_stock.informacionVoucher == vi && counterfoil_no_stock.activo
    }

    void "buy vouchers"() {
        Voucher v = clientService.comprarVoucher(clientId, counterfoil_active)
        counterfoil_active = Talonario.findById(counterfoil_active.id)
        expect:"Voucher bought correctly"
        v != null && counterfoil_active.stock == 2 && counterfoil_active.informacionVoucher.id == vi.id && counterfoil_active.getVouchers().size() == 1 && counterfoil_active. getVouchers()[0] == v && counterfoil_active.activo
    }

    void "buy vouchers without stock"() {
        when:
        Voucher v = clientService.comprarVoucher(clientId, counterfoil_no_stock)
        then: "Throw error"
        thrown RuntimeException
    }

    void "activate counterfoil"() {
        counterfoilService.activate(counterfoil_inactive.id)
        counterfoil_inactive = Talonario.findById(counterfoil_inactive.id)
        expect:"Counterfoil is active"
        counterfoil_inactive.activo
    }

    void "activate and deactivate counterfoil"() {
        counterfoilService.activate(counterfoil_inactive.id)
        counterfoilService.deactivate(counterfoil_inactive.id)
        counterfoil_inactive = Talonario.findById(counterfoil_inactive.id)
        expect:"Counterfoil is not active"
        !counterfoil_inactive.activo
    }
}
