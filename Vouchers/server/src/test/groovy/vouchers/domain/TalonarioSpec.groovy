package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Cliente
import vouchers.ClienteService
import vouchers.InformacionVoucher
import vouchers.Item
import vouchers.Negocio
import vouchers.Producto
import vouchers.Talonario

class TalonarioSpec extends Specification implements DomainUnitTest<Talonario> {

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
        Negocio negocio = new Negocio()
        Talonario talonario = new Talonario(stock: 6, informacionVoucher: iv, negocio: negocio)
        expect:"Talonario construido correctamente"
        talonario != null && talonario.getNegocio() == negocio && talonario.getInformacionVoucher() == iv && !talonario.getActivo() && talonario.getStock() == 6
    }


    void "talonario tiene que tener una informacion de voucher"() {
        InformacionVoucher iv = crearInformacionVoucher()
        Negocio negocio = new Negocio()
        Talonario talonario = new Talonario(stock: 6, negocio: negocio)
        when:
        talonario.informacionVoucher = null
        then:
        !talonario.validate(['informacionVoucher'])
        talonario.errors['informacionVoucher'].code == 'nullable'
    }

    void "talonario tiene que tener un negocio asociado"() {
        InformacionVoucher iv = crearInformacionVoucher()
        Negocio negocio = new Negocio()
        Talonario talonario = new Talonario(stock: 6, negocio: negocio)
        when:
        talonario.negocio = null
        then:
        !talonario.validate(['negocio'])
        talonario.errors['negocio'].code == 'nullable'
    }

    void "talonario tiene 0 vouchers vendidos al ser creado"() {
        InformacionVoucher iv = crearInformacionVoucher()
        Negocio negocio = new Negocio()
        Talonario talonario = new Talonario(stock: 6, informacionVoucher: iv, negocio: negocio)
        expect:"Talonario tiene 0 vouchers vendidos"
        talonario != null && talonario.cantidadVendida() == 0
    }

    void "talonario tiene 1 voucher vendido al ser creado y comprado 1"() {
        InformacionVoucher iv = crearInformacionVoucher()
        Negocio negocio = new Negocio()
        Talonario talonario = new Talonario(stock: 6, informacionVoucher: iv, negocio: negocio)
        Cliente cliente = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        talonario.comprarVoucher(cliente)
        expect:"Talonario tiene 1 vouchers vendidos"
        talonario.cantidadVendida() == 1
    }


}
