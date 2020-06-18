package vouchers.services

import enums.ProductType
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification
import vouchers.Address
import vouchers.Business
import vouchers.BusinessService
import vouchers.Client
import vouchers.ClientService
import vouchers.Counterfoil
import vouchers.CounterfoilService
import vouchers.Country
import vouchers.Item
import vouchers.Product
import vouchers.Voucher
import vouchers.VoucherInformation

@Integration
@Rollback
class CounterfoilServiceSpec extends Specification{

    @Autowired
    BusinessService businessService
    ClientService clientService
    CounterfoilService counterfoilService

    private static Boolean setupIsDone = false
    static Long businessId
    static Counterfoil counterfoil_active
    static Counterfoil counterfoil_inactive
    static Counterfoil counterfoil_no_stock
    static VoucherInformation vi
    static Long clientId

    def setup() {
        if (setupIsDone) return

        Business business = new Business()
        business.name = "Blue Dog"
        business.email = "sales@bluedog.com"
        business.password = "1234"
        business.verifiedAccount = true
        business.phone_number = "1234"
        business.category = "Cervezería"
        Address newAddress = new Address()
        newAddress.street = "calle falsa"
        newAddress.number = "123"
        newAddress.apartment = "11D"
        newAddress.province = "Buenos Aires"
        Country country = new Country()
        country.name = "Argentina"
        newAddress.country = country

        business.address = newAddress
        business.website = "bluedog.com"

        Product product = new Product()
        product.description = "Hamburguesa con cebolla, cheddar, huevo, jamón, todo."
        product.name = "Hamburguesa Blue Dog"
        product.type = ProductType.FAST_FOOD
        business.addToProducts(product)
        business.save(flush: true, failOnError: true)

        Item item = new Item(product: product, quantity: 1)
        vi = new VoucherInformation(price: 400, description: "Promo verano", validFrom: new Date('2020/08/01'), validUntil:  new Date('2020/08/15'))
        vi.addToItems(item)
        counterfoil_active = new Counterfoil(voucherInformation: vi, stock: 3, active: true)
        business.addToCounterfoils(counterfoil_active)
        counterfoil_inactive = new Counterfoil(voucherInformation: vi, stock: 10, active: false)
        business.addToCounterfoils(counterfoil_inactive)
        counterfoil_no_stock = new Counterfoil(voucherInformation: vi, stock: 0, active: true)
        business.addToCounterfoils(counterfoil_no_stock)

        business.save(flush: true, failOnError: true)

        Client client = new Client(fullName: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        client.verifiedAccount = true
        client.save(flush:true, failOnError:true)

        setupIsDone = true
        clientId = client.id
        businessId = business.id
    }

    def cleanup() {
    }

    void "setup test"() {
        expect:
        Business.count() == 1
        Product.count() == 1
        Counterfoil.count() == 3
    }

    void "constructor"() {
        expect:"counterfoils constructed correctly"
        counterfoil_active != null && counterfoil_active.stock == 3 && counterfoil_active.voucherInformation == vi && counterfoil_active.active
        counterfoil_inactive != null && counterfoil_inactive.stock == 10 && counterfoil_inactive.voucherInformation == vi && !counterfoil_inactive.active
        counterfoil_no_stock != null && counterfoil_no_stock.stock == 0 && counterfoil_no_stock.voucherInformation == vi && counterfoil_no_stock.active
    }

    void "buy vouchers"() {
        Voucher v = clientService.buyVoucher(clientId, counterfoil_active)
        expect:"Voucher bought correctly"
        v != null && counterfoil_active.stock == 2 && counterfoil_active.voucherInformation == vi && counterfoil_active.getVouchers().size() == 1 && counterfoil_active. getVouchers()[0] == v && counterfoil_active.active
    }

    void "buy vouchers without stock"() {
        when:
        Voucher v = clientService.buyVoucher(clientId, counterfoil_no_stock)
        then: "Throw error"
        thrown RuntimeException
    }

    void "activate counterfoil"() {
        counterfoilService.activate(counterfoil_inactive.id)
        expect:"Counterfoil is active"
        counterfoil_inactive.active
    }

    void "activate and deactivate counterfoil"() {
        counterfoilService.activate(counterfoil_inactive.id)
        counterfoilService.deactivate(counterfoil_inactive.id)
        expect:"Counterfoil is not active"
        !counterfoil_inactive.active
    }
}
