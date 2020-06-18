package vouchers.services

import enums.ProductType
import enums.states.VoucherState
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
import vouchers.Country
import vouchers.Item
import vouchers.Product
import vouchers.Voucher
import vouchers.VoucherInformation

@Integration
@Rollback
class BusinessServiceSpec extends Specification{

    @Autowired
    BusinessService businessService
    ClientService clientService

    private static Boolean setupIsDone = false
    static Long businessId
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
        VoucherInformation vi = new VoucherInformation(price: 400, description: "Promo verano", validFrom: new Date('2020/08/01'), validUntil:  new Date('2020/08/15'))
        vi.addToItems(item)
        Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 3, isActive: true)
        business.addToCounterfoils(counterfoil)
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
        Counterfoil.count() == 1
    }

    void "confirm voucher retirement"() {
        Counterfoil counterfoil = Counterfoil.findById(1)
        Voucher v = clientService.buyVoucher(clientId, counterfoil)
        Client client = Client.get(clientId)
        clientService.retireVoucher(clientId, v)
        businessService.confirmRetireVoucher(businessId, v)
        expect:"Vouchers status in retired"
        v != null && client.getVouchers().size() == 1 && client.getVouchers()[0] == v && v.getState() == VoucherState.RETIRED
    }

    void "confirm voucher retirement before client retires it"() {
        Counterfoil counterfoil = Counterfoil.findById(1)
        Voucher v = clientService.buyVoucher(clientId, counterfoil)
        when:
        businessService.confirmRetireVoucher(businessId, v)
        then: "Throw error"
        thrown RuntimeException
        v.state == VoucherState.BOUGHT
    }

    void "confirm voucher from other business"() {
        Business b2 = new Business(name: "Mc", phone_number: "123412341234", address: new Address(street: "Corrientes", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "mc@gmail.com", password: "mc1234")
        b2.save()
        Counterfoil counterfoil = Counterfoil.findById(1)
        Voucher v = clientService.buyVoucher(clientId, counterfoil)
        Client client = Client.get(clientId)
        clientService.retireVoucher(clientId, v)
        when:
        businessService.confirmRetireVoucher(b2.id, v)
        then: "Throw error"
        thrown RuntimeException
        v.state == VoucherState.PENDING_CONFIRMATION
    }
}
