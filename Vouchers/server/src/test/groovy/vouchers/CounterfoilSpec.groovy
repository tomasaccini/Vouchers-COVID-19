package vouchers

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CounterfoilSpec extends Specification implements DomainUnitTest<Counterfoil> {

    def createVoucherInformation() {
        Product p1 = new Product(name:"Hamburguesa", description: "Doble cheddar")
        Product p2 = new Product(name:"Pinta cerveza", description: "Cerveza artesanal de la casa")
        Item i1 = new Item(product: p1, quantity: 1)
        Item i2 = new Item(product: p2, quantity: 2)

        VoucherInformation vi = new VoucherInformation(price: 400, description: "Promo verano", valid_from: new Date('2020/08/01'), valid_until: new Date('2020/12/31'), items: [i1, i2])
        vi
    }

    def createClient(){
        new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
    }

    def cleanup() {
    }

    void "constructor"() {
        VoucherInformation vi = createVoucherInformation()
        Counterfoil c = new Counterfoil(voucherInformation: vi, stock: 3)
        expect:"counterfoil constructed correctly"
        c != null && c.stock == 3 && c.voucherInformation == vi
    }

    void "buy vouchers"() {
        VoucherInformation vi = createVoucherInformation()
        Client client = createClient()
        Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 3)
        Voucher v = client.buyVoucher(counterfoil)
        expect:"Voucher bought correctly"
        v != null && counterfoil.stock == 2 && counterfoil.voucherInformation == vi && counterfoil.getVouchers().size() == 1 && counterfoil. getVouchers()[0] == v && client.getVouchers().size() == 1 && client.getVouchers()[0] == v
    }

    void "buy vouchers without stock"() {
        VoucherInformation vi = createVoucherInformation()
        Client client = createClient()
        Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 0)
        when:
            client.buyVoucher(counterfoil)
        then: "Throw error"
            thrown RuntimeException
    }
}
