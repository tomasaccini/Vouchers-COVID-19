package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import enums.states.ReclamoState
import vouchers.Direccion
import vouchers.Negocio
import vouchers.Cliente
import vouchers.Reclamo
import vouchers.Talonario

import vouchers.Item
import vouchers.Producto
import vouchers.Voucher
import vouchers.InformacionVoucher

class ReclamoSpec extends Specification implements DomainUnitTest<Reclamo> {

    def createVoucherInformation(valid_until = new Date('2020/12/31')) {
        Producto p1 = new Producto(nombre:"Hamburguesa", descripcion: "Doble cheddar")
        Producto p2 = new Producto(nombre:"Pinta cerveza", descripcion: "Cerveza artesanal de la casa")
        Item i1 = new Item(producto: p1, cantidad: 1)
        Item i2 = new Item(producto: p2, cantidad: 2)

        InformacionVoucher vi = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: valid_until, items: [i1, i2])
        vi
    }

    def createComplaint(Negocio b, Cliente c) {
        InformacionVoucher vi = createVoucherInformation()
        Talonario counterfoil = new Talonario(informacionVoucher: vi, stock: 3)
        b.addToTalonarios(counterfoil)
        Voucher v = new Voucher(informacionVoucher: vi)
        c.addToVouchers(v)
        Reclamo complaint = new Reclamo(cliente: c, negocio: b, descripcion: "Description of my problem")
        v.reclamo = complaint
        complaint
    }

    void "constructor"() {
        Negocio b = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(full_name: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo complaint = createComplaint(b, c)
        expect:"complaint constructed correctly"
        complaint != null && complaint.state == ReclamoState.Abierto && complaint.negocio == b && complaint.cliente == c && complaint.mensajes.size() == 0
    }

    void "add message from business"() {
        Negocio b = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(full_name: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo complaint = createComplaint(b, c)
        String msg = "First msg from business"
        complaint.agregarMensaje(msg, b)
        expect:"complaint message added correctly"
        complaint != null && complaint.state == ReclamoState.Respondido && complaint.negocio == b && complaint.cliente == c && complaint.mensajes.size() == 1 && complaint.mensajes[0].owner == b && complaint.mensajes[0].message == msg
    }

    void "add message from client"() {
        Negocio b = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(full_name: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo complaint = createComplaint(b, c)
        String msg = "First msg from client"
        complaint.agregarMensaje(msg, c)
        expect:"complaint message added correctly"
        complaint != null && complaint.state == ReclamoState.Respondido && complaint.negocio == b && complaint.cliente == c && complaint.mensajes.size() == 1 && complaint.mensajes[0].owner == c && complaint.mensajes[0].message == msg
    }

    void "add two messages"() {
        Negocio b = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(full_name: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo complaint = createComplaint(b, c)
        String msg1 = "First msg from business"
        String msg2 = "Second msg from client"
        complaint.agregarMensaje(msg1, b)
        complaint.agregarMensaje(msg2, c)
        expect:"complaint messages added correctly"
        complaint != null && complaint.state == ReclamoState.Respondido && complaint.negocio == b && complaint.cliente == c && complaint.mensajes.size() == 2 && complaint.mensajes[0].owner == b && complaint.mensajes[0].message == msg1 && complaint.mensajes[1].owner == c && complaint.mensajes[1].message == msg2
    }

    void "add message from business and close"() {
        Negocio b = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(full_name: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo complaint = createComplaint(b, c)
        String msg = "First msg from business"
        complaint.agregarMensaje(msg, b)
        complaint.cerrar()
        expect:"complaint message added correctly and closed"
        complaint != null && complaint.state == ReclamoState.Cerrado && complaint.negocio == b && complaint.cliente == c && complaint.mensajes.size() == 1 && complaint.mensajes[0].owner == b && complaint.mensajes[0].message == msg
    }

    void "add message from business, close and reopen"() {
        Negocio b = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(full_name: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo complaint = createComplaint(b, c)
        String msg = "First msg from business"
        complaint.agregarMensaje(msg, b)
        complaint.cerrar()
        complaint.reabrir()
        expect:"complaint message added correctly, closed and reopened"
        complaint != null && complaint.state == ReclamoState.Respondido && complaint.negocio == b && complaint.cliente == c && complaint.mensajes.size() == 1 && complaint.mensajes[0].owner == b && complaint.mensajes[0].message == msg
    }

    void "close and reopen without messages"() {
        Negocio b = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(full_name: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo complaint = createComplaint(b, c)
        complaint.cerrar()
        complaint.reabrir()
        expect:"complaint message added correctly, closed and reopened"
        complaint != null && complaint.state == ReclamoState.Abierto && complaint.negocio == b && complaint.cliente == c && complaint.mensajes.size() == 0
    }
}
