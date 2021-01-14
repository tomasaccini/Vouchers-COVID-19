package vouchers.domain

import enums.states.VoucherEstado
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.InformacionVoucher
import vouchers.Item
import vouchers.Producto
import vouchers.Voucher

class VoucherSpec extends Specification implements DomainUnitTest<Voucher> {

    def crearInformacionVoucher(validoHasta = new Date('2020/12/31')) {
        Producto p1 = new Producto(nombre:"Hamburguesa", descripcion: "Doble cheddar")
        Producto p2 = new Producto(nombre:"Pinta cerveza", descripcion: "Cerveza artesanal de la casa")
        Item i1 = new Item(producto: p1, cantidad: 1)
        Item i2 = new Item(producto: p2, cantidad: 2)

        InformacionVoucher iv = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: validoHasta, items: [i1, i2])
        iv
    }

    void "constructor"() {
        InformacionVoucher iv = crearInformacionVoucher()
        Voucher v = new Voucher(informacionVoucher: iv)
        expect:"voucher construido correctamente"
        v != null && v.reclamo == null && v.estado == VoucherEstado.Comprado
    }

    void "voucher tiene que tener una informacion de voucher"() {
        InformacionVoucher vi = crearInformacionVoucher()
        Voucher v = new Voucher(informacionVoucher: vi)
        when:
        v.informacionVoucher = null
        then:
        !v.validate(['informacionVoucher'])
        v.errors['informacionVoucher'].code == 'nullable'
    }

}
