package vouchers

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import enums.states.VoucherState

class BusinessSpec extends Specification implements DomainUnitTest<Business> {

    def createVoucherInformation() {
        Product p1 = new Product(name:"Hamburguesa", description: "Doble cheddar")
        Product p2 = new Product(name:"Pinta cerveza", description: "Cerveza artesanal de la casa")
        Item i1 = new Item(product: p1, quantity: 1)
        Item i2 = new Item(product: p2, quantity: 2)

        VoucherInformation vi = new VoucherInformation(price: 400, description: "Promo verano", valid_from: new Date('2020/08/01'), valid_until: new Date('2020/12/31'), items: [i1, i2])
        vi
    }

    def cleanup() {
    }

    void "constructor"() {
        Business b = new Business(name: "Burger", phone_number: "123412341234", address: new Address(street: "Libertador", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "burger@gmail.com", password: "burger1234")
        expect:"business constructed correctly"
        b != null && b.name == "Burger" && b.email == "burger@gmail.com" && b.password == "burger1234" && b.phone_number == "123412341234" && b.category == "Restaurant" && !b.verified_account
    }

    void "verify account"() {
        Business b = new Business(name: "Burger", phone_number: "123412341234", address: new Address(street: "Libertador", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "burger@gmail.com", password: "burger1234")
        b.verify_account()
        expect:"account is verified"
        b.verified_account
    }

    void "confirm voucher retirement"() {
        Client client = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        Business b = new Business(name: "Burger", phone_number: "123412341234", address: new Address(street: "Libertador", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "burger@gmail.com", password: "burger1234")
        VoucherInformation vi = createVoucherInformation()
        Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 3, active: true)
        b.addCounterfoil(counterfoil)
        Voucher v = client.buyVoucher(counterfoil)
        client.retireVoucher(v)
        b.confirmRetireVoucher(v)
        expect:"Vouchers status in retired"
        v != null && client.getVouchers().size() == 1 && client.getVouchers()[0] == v && v.getState() == VoucherState.RETIRED
    }

    void "confirm voucher retirement before client retires it"() {
        Client client = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        Business b = new Business(name: "Burger", phone_number: "123412341234", address: new Address(street: "Libertador", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "burger@gmail.com", password: "burger1234")
        VoucherInformation vi = createVoucherInformation()
        Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 3, active: true)
        b.addCounterfoil(counterfoil)
        Voucher v = client.buyVoucher(counterfoil)
        when:
            b.confirmRetireVoucher(v)
        then: "Throw error"
            thrown RuntimeException
            v.state == VoucherState.BOUGHT
    }

    void "confirm voucher from other business"() {
        Client client = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        Business b1 = new Business(name: "Burger", phone_number: "123412341234", address: new Address(street: "Libertador", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "burger@gmail.com", password: "burger1234")
        Business b2 = new Business(name: "Mc", phone_number: "123412341234", address: new Address(street: "Corrientes", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "mc@gmail.com", password: "mc1234")
        VoucherInformation vi = createVoucherInformation()
        Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 3, active: true)
        b1.addCounterfoil(counterfoil)
        Voucher v = client.buyVoucher(counterfoil)
        client.retireVoucher(v)
        when:
            b2.confirmRetireVoucher(v)
        then: "Throw error"
            thrown RuntimeException
            v.state == VoucherState.PENDING_CONFIRMATION
    }
}
