package vouchers

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import enums.states.VoucherState

class VoucherSpec extends Specification implements DomainUnitTest<Voucher> {

    def setup() {
    }

    def cleanup() {
    }

    def createVoucherInformation(valid_until = new Date('2020/12/31')) {
        Product p1 = new Product(name:"Hamburguesa", description: "Doble cheddar")
        Product p2 = new Product(name:"Pinta cerveza", description: "Cerveza artesanal de la casa")
        Item i1 = new Item(product: p1, quantity: 1)
        Item i2 = new Item(product: p2, quantity: 2)

        VoucherInformation vi = new VoucherInformation(price: 400, description: "Promo verano", valid_from: new Date('2020/08/01'), valid_until: valid_until, items: [i1, i2])
        vi
    }

    void "constructor"() {
        VoucherInformation vi = createVoucherInformation()
        Voucher v = new Voucher(voucherInformation: vi)
        expect:"voucher constructed correctly"
        v != null && v.complaint == null && v.state == VoucherState.BOUGHT
    }

    void "voucher must have voucher information"() {
        VoucherInformation vi = createVoucherInformation()
        Voucher v = new Voucher(voucherInformation: vi)
        when:
        v.voucherInformation = null
        then:
        !v.validate(['voucherInformation'])
        v.errors['voucherInformation'].code == 'nullable'
    }

}
