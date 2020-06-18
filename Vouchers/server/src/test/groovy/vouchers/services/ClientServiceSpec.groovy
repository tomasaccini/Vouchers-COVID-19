package vouchers.services

import enums.ProductType
import enums.states.VoucherState
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification
import vouchers.Address
import vouchers.Business
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
class ClientServiceSpec extends Specification{

    @Autowired
    ClientService clientService

    private static Boolean setupIsDone = false
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
    }

    def cleanup() {
    }

    void "setup test"() {
        expect:
        Business.count() == 1
        Product.count() == 1
        Counterfoil.count() == 1
    }

    void "buy one voucher"() {
        Counterfoil counterfoil = Counterfoil.findById(1)

        Voucher v = clientService.buyVoucher(clientId, counterfoil)
        Client client = Client.get(clientId)
        expect:"Voucher bought correctly"
        v?.id != null
        client.vouchers.size() == 1
        counterfoil.vouchers.size() == 1
    }

    void "buy two vouchers"() {
        Counterfoil counterfoil = Counterfoil.findById(1)

        Voucher v1 = clientService.buyVoucher(clientId, counterfoil)
        Voucher v2 = clientService.buyVoucher(clientId, counterfoil)
        Client client = Client.get(clientId)
        expect:"Voucher bought correctly"
        v1?.id != null
        v2?.id != null
        client.vouchers.size() == 2
        counterfoil.vouchers.size() == 2
    }

    void "retire Voucher"() {
        Counterfoil counterfoil = Counterfoil.findById(1)

        Voucher v = clientService.buyVoucher(clientId, counterfoil)
        Client client = Client.get(clientId)
        clientService.retireVoucher(clientId, v)
        expect:"Vouchers status in pending retire"
        v != null && client.getVouchers().size() == 1 && client.getVouchers()[0] == v && v.getState() == VoucherState.PENDING_CONFIRMATION
    }

    void "test expiration of voucher"() {
        Business b = Business.findById(1)
        VoucherInformation vi = new VoucherInformation(price: 400, description: "Promo verano", validFrom: new Date('2019/01/01'), validUntil:  new Date('2020/01/01'))
        vi.addToItems(Item.findById(1))
        Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 3, isActive: true)
        b.addToCounterfoils(counterfoil)
        b.save(flush: true, failOnError: true)
        Voucher v = clientService.buyVoucher(clientId, counterfoil)
        when:
        clientService.retireVoucher(clientId, v)
        then: "Throw error"
        thrown RuntimeException
        v.state == VoucherState.EXPIRED
    }

    void "test retire voucher from other client"() {
        Client c1 = Client.get(clientId)
        Client c2 = new Client(fullName: "Mariano Iudica", email: "iudica@gmail.com", password: "iudica1234")
        c2.verifiedAccount = true
        c2.save(flush:true, failOnError:true)
        Counterfoil counterfoil = Counterfoil.findById(1)
        Voucher v = clientService.buyVoucher(c1.id, counterfoil)
        when:
        clientService.retireVoucher(c2.id, v)
        then: "Throw error"
        thrown RuntimeException
        v.state == VoucherState.BOUGHT
    }
}
