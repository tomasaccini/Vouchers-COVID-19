package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Direccion
import vouchers.InformacionVoucher
import vouchers.Item
import vouchers.Negocio
import vouchers.Producto
import vouchers.Talonario

class NegocioSpec extends Specification implements DomainUnitTest<Negocio> {

    def crearInformacionVoucher(descripcion = null) {
        Producto p1 = new Producto(nombre: "Hamburguesa", descripcion: "Doble cheddar")
        Producto p2 = new Producto(nombre: "Pinta cerveza", descripcion: "Cerveza artesanal de la casa")
        Item i1 = new Item(producto: p1, cantidad: 1)
        Item i2 = new Item(producto: p2, cantidad: 2)

        InformacionVoucher iv = new InformacionVoucher(precio: 400, descripcion: descripcion, validoDesde: new Date('2020/08/01'), validoHasta: new Date('2030/12/31'), items: [i1, i2])
        iv
    }

    void "constructor"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        expect: "negocio construido correctamente"
        n != null && n.nombre == "Burger" && n.email == "burger@gmail.com" && n.contrasenia == "burger1234" && n.numeroTelefonico == "123412341234" && n.categoria == "Restaurant" && !n.cuentaVerificada
    }

    void "verificar cuenta"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        n.verificarCuenta()
        expect: "la cuenta esta verificada"
        n.cuentaVerificada
    }

    void "buscar talonarios con descripcion en negocio sin talonarios"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        expect: "el negocio no tiene talonarios con descripcion"
        !n.tieneTalonarioConDescripcion()
    }

    void "buscar talonarios con descripcion en negocio con talonarios sin descripcion"() {
        InformacionVoucher iv = crearInformacionVoucher()
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        n.save()
        Talonario talonario = new Talonario(stock: 6, informacionVoucher: iv, negocio: n)
        talonario.save()
        expect: "el negocio no tiene talonarios con descripcion"
        !n.tieneTalonarioConDescripcion()
    }
}
