package vouchers

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ClientSpec extends Specification implements DomainUnitTest<Client> {

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
        Client c = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        expect:"client constructed correctly"
            c != null && c.full_name == "Ricardo Fort" && c.email == "ricki@gmail.com" && c.password == "ricki1234" && c.phone_number == null && !c.verified_account
    }

    void "verify account"() {
        Client c = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        c.verify_account()
        expect:"account is verified"
            c.verified_account
    }

    void "buy one voucher"() {
        Client client = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        VoucherInformation vi = createVoucherInformation()
        Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 3)
        Voucher v = client.buyVoucher(counterfoil)
        expect:"Voucher bought correctly"
        v != null && client.getVouchers().size() == 1 && client.getVouchers()[0] == v
    }

    void "buy two vouchers"() {
        Client client = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        VoucherInformation vi = createVoucherInformation()
        Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 3)
        Voucher v1 = client.buyVoucher(counterfoil)
        Voucher v2 = client.buyVoucher(counterfoil)
        expect:"Vouchers bought correctly"
        v1 != null && v2 != null && client.getVouchers().size() == 2 && client.getVouchers()[0] == v1 && client.getVouchers()[1] == v2
    }
}
