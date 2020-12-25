package vouchers.domain

import enums.InteraccionType
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Cliente
import vouchers.InteraccionClienteTalonario
import vouchers.Talonario
import vouchers.Item
import vouchers.Producto
import vouchers.InformacionVoucher

class ClienteTalonarioInteractionSpec extends Specification implements DomainUnitTest<InteraccionClienteTalonario> {

    def createVoucherInformation(valid_until = new Date('2020/12/31')) {
        Producto p1 = new Producto(nombre:"Hamburguesa", descripcion: "Doble cheddar")
        Producto p2 = new Producto(nombre:"Pinta cerveza", descripcion: "Cerveza artesanal de la casa")
        Item i1 = new Item(producto: p1, cantidad: 1)
        Item i2 = new Item(producto: p2, cantidad: 2)

        InformacionVoucher vi = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: valid_until, items: [i1, i2])
        vi
    }

    def cleanup() {
    }

    void "constructor view"() {
        Cliente c = new Cliente(full_name: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        InformacionVoucher vi = createVoucherInformation()
        Talonario counterfoil = new Talonario(informacionVoucher: vi, stock: 3)
        InteraccionClienteTalonario cci = new InteraccionClienteTalonario(cliente: c, talonario: counterfoil, interaccion: InteraccionType.Visto)
        expect:"interaction constructed correctly"
        cci != null && cci.cliente == c && cci.talonario == counterfoil && cci.dateCreated != null && cci.interaccion == InteraccionType.Visto
    }

    void "constructor buy"() {
        Cliente c = new Cliente(full_name: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        InformacionVoucher vi = createVoucherInformation()
        Talonario counterfoil = new Talonario(informacionVoucher: vi, stock: 3)
        InteraccionClienteTalonario cci = new InteraccionClienteTalonario(cliente: c, talonario: counterfoil, interaccion: InteraccionType.Comprado)
        expect:"interaction constructed correctly"
        cci != null && cci.cliente == c && cci.talonario == counterfoil && cci.dateCreated != null && cci.interaccion == InteraccionType.Comprado
    }
}
