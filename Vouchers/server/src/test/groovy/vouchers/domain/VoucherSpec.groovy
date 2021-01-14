package vouchers.domain

import enums.states.VoucherEstado
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Cliente
import vouchers.InformacionVoucher
import vouchers.Item
import vouchers.Negocio
import vouchers.Producto
import vouchers.Talonario
import vouchers.Voucher

class VoucherSpec extends Specification implements DomainUnitTest<Voucher> {

    def crearInformacionVoucher(validoHasta = new Date('2030/12/31')) {
        Producto p1 = new Producto(nombre: "Hamburguesa", descripcion: "Doble cheddar")
        Producto p2 = new Producto(nombre: "Pinta cerveza", descripcion: "Cerveza artesanal de la casa")
        Item i1 = new Item(producto: p1, cantidad: 1)
        Item i2 = new Item(producto: p2, cantidad: 2)

        InformacionVoucher iv = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: validoHasta, items: [i1, i2])
        iv
    }

    void "constructor"() {
        InformacionVoucher iv = crearInformacionVoucher()
        Voucher v = new Voucher(informacionVoucher: iv)
        expect: "voucher construido correctamente"
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

    void "voucher creandolo a partir de una compra tiene el correcto negocio como duenio"() {
        InformacionVoucher iv = crearInformacionVoucher()
        Negocio negocio = new Negocio()
        Talonario talonario = new Talonario(stock: 6, informacionVoucher: iv, negocio: negocio, activo: true)
        Cliente cliente = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Voucher v = talonario.comprarVoucher(cliente)
        expect: "Voucher tiene el negocio correcto como duenio"
        v.perteneceAlNegocio(negocio.id)
    }

    void "voucher expirado es detectado correctamente como expirado"() {
        InformacionVoucher iv = crearInformacionVoucher(new Date('2000/12/31'))
        Voucher v = new Voucher(informacionVoucher: iv)
        expect: "Voucher esta expirado"
        v.estaExpirado()
        v.estado == VoucherEstado.Expirado
    }


    void "voucher no expirado es detectado correctamente como no expirado"() {
        InformacionVoucher iv = crearInformacionVoucher(new Date('2030/12/31'))
        Negocio negocio = new Negocio()
        Talonario talonario = new Talonario(stock: 6, informacionVoucher: iv, negocio: negocio, activo: true)
        Cliente cliente = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Voucher v = talonario.comprarVoucher(cliente)
        expect: "Voucher no esta expirado"
        !v.estaExpirado()
        v.estado == VoucherEstado.Comprado
    }

}
