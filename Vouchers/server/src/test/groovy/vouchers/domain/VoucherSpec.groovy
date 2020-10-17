package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import enums.states.VoucherState
import vouchers.Item
import vouchers.Producto
import vouchers.Voucher
import vouchers.InformacionVoucher

class VoucherSpec extends Specification implements DomainUnitTest<Voucher> {

    def setup() {
    }

    def cleanup() {
    }

    def createVoucherInformation(valid_until = new Date('2020/12/31')) {
        Producto p1 = new Producto(nombre:"Hamburguesa", descripcion: "Doble cheddar")
        Producto p2 = new Producto(nombre:"Pinta cerveza", descripcion: "Cerveza artesanal de la casa")
        Item i1 = new Item(producto: p1, cantidad: 1)
        Item i2 = new Item(producto: p2, cantidad: 2)

        InformacionVoucher vi = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: valid_until, items: [i1, i2])
        vi
    }

    void "constructor"() {
        InformacionVoucher vi = createVoucherInformation()
        Voucher v = new Voucher(informacionVoucher: vi)
        expect:"voucher constructed correctly"
        v != null && v.reclamo == null && v.state == VoucherState.Comprado
    }

    void "voucher must have voucher information"() {
        InformacionVoucher vi = createVoucherInformation()
        Voucher v = new Voucher(informacionVoucher: vi)
        when:
        v.informacionVoucher = null
        then:
        !v.validate(['voucherInformation'])
        v.errors['voucherInformation'].code == 'nullable'
    }

}
