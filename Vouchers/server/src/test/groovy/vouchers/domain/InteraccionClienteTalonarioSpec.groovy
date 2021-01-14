package vouchers.domain

import enums.TipoInteraccion
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.*

class InteraccionClienteTalonarioSpec extends Specification implements DomainUnitTest<InteraccionClienteTalonario> {

    def crearInformacionVoucher(validoHasta = new Date('2020/12/31')) {
        Producto p1 = new Producto(nombre:"Hamburguesa", descripcion: "Doble cheddar")
        Producto p2 = new Producto(nombre:"Pinta cerveza", descripcion: "Cerveza artesanal de la casa")
        Item i1 = new Item(producto: p1, cantidad: 1)
        Item i2 = new Item(producto: p2, cantidad: 2)

        InformacionVoucher iv = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: validoHasta, items: [i1, i2])
        iv
    }

    def cleanup() {
    }

    void "constructor interaccion visto"() {
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        InformacionVoucher iv = crearInformacionVoucher()
        Talonario talonario = new Talonario(informacionVoucher: iv, stock: 3)
        InteraccionClienteTalonario ict = new InteraccionClienteTalonario(cliente: c, talonario: talonario, interaccion: TipoInteraccion.Visto)
        expect:"interaction constructed correctly"
        ict != null && ict.cliente == c && ict.talonario == talonario && ict.dateCreated != null && ict.interaccion == TipoInteraccion.Visto
    }

    void "constructor interaccion comprado"() {
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        InformacionVoucher iv = crearInformacionVoucher()
        Talonario talonario = new Talonario(informacionVoucher: iv, stock: 3)
        InteraccionClienteTalonario ict = new InteraccionClienteTalonario(cliente: c, talonario: talonario, interaccion: TipoInteraccion.Comprado)
        expect:"interaction constructed correctly"
        ict != null && ict.cliente == c && ict.talonario == talonario && ict.dateCreated != null && ict.interaccion == TipoInteraccion.Comprado
    }
}
