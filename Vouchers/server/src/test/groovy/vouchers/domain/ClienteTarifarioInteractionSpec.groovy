package vouchers.domain

import enums.InteraccionType
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Cliente
import vouchers.InteraccionClienteTarifario
import vouchers.Tarifario
import vouchers.Item
import vouchers.Producto
import vouchers.InformacionVoucher

class ClienteTarifarioInteractionSpec extends Specification implements DomainUnitTest<InteraccionClienteTarifario> {

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
        Tarifario counterfoil = new Tarifario(informacionVoucher: vi, stock: 3)
        InteraccionClienteTarifario cci = new InteraccionClienteTarifario(cliente: c, tarifario: counterfoil, interaccion: InteraccionType.Visto)
        expect:"interaction constructed correctly"
        cci != null && cci.cliente == c && cci.tarifario == counterfoil && cci.dateCreated != null && cci.interaccion == InteraccionType.Visto
    }

    void "constructor buy"() {
        Cliente c = new Cliente(full_name: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        InformacionVoucher vi = createVoucherInformation()
        Tarifario counterfoil = new Tarifario(informacionVoucher: vi, stock: 3)
        InteraccionClienteTarifario cci = new InteraccionClienteTarifario(cliente: c, tarifario: counterfoil, interaccion: InteraccionType.Comprado)
        expect:"interaction constructed correctly"
        cci != null && cci.cliente == c && cci.tarifario == counterfoil && cci.dateCreated != null && cci.interaccion == InteraccionType.Comprado
    }
}
