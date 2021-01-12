package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import enums.states.ReclamoEstado
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

    def crearInformacionVoucher(validoHasta = new Date('2020/12/31')) {
        Producto p1 = new Producto(nombre:"Hamburguesa", descripcion: "Doble cheddar")
        Producto p2 = new Producto(nombre:"Pinta cerveza", descripcion: "Cerveza artesanal de la casa")
        Item i1 = new Item(producto: p1, cantidad: 1)
        Item i2 = new Item(producto: p2, cantidad: 2)

        InformacionVoucher iv = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: validoHasta, items: [i1, i2])
        iv
    }

    def crearReclamo(Negocio n, Cliente c) {
        InformacionVoucher iv = crearInformacionVoucher()
        Talonario talonario = new Talonario(informacionVoucher: iv, stock: 3)
        n.addToTalonarios(talonario)
        Voucher v = new Voucher(informacionVoucher: iv)
        c.addToVouchers(v)
        Reclamo reclamo = new Reclamo(cliente: c, negocio: n, descripcion: "Descripcion de mi problema", voucher: v, fechaCreacion: new Date())
        v.reclamo = reclamo
        reclamo
    }

    void "constructor"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo = crearReclamo(n, c)
        expect:"reclamo construido correctamente"
        reclamo != null && reclamo.estado == ReclamoEstado.Abierto && reclamo.negocio == n && reclamo.cliente == c && reclamo.mensajes.size() == 0
    }

    void "agregar mensaje del negocio"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo = crearReclamo(n, c)
        String mensaje = "Primer mensaje del negocio"
        reclamo.agregarMensaje(mensaje, n)
        expect:"mensaje agregado correctamente al reclamo"
        reclamo != null && reclamo.estado == ReclamoEstado.Respondido && reclamo.negocio == n && reclamo.cliente == c && reclamo.mensajes.size() == 1 && reclamo.mensajes[0].duenio == n && reclamo.mensajes[0].texto == mensaje
    }

    void "agregar mensaje del cliente"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo = crearReclamo(n, c)
        String mensaje = "Primer mensaje del cliente"
        reclamo.agregarMensaje(mensaje, c)
        expect:"mensaje agregado correctamente al reclamo"
        reclamo != null && reclamo.estado == ReclamoEstado.Abierto && reclamo.negocio == n && reclamo.cliente == c && reclamo.mensajes.size() == 1 && reclamo.mensajes[0].duenio == c && reclamo.mensajes[0].texto == mensaje
    }

    void "agregar dos mensajes"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo = crearReclamo(n, c)
        String mensaje1 = "Primer mensaje del negocio"
        String mensaje2 = "Segundo mensaje del cliente"
        reclamo.agregarMensaje(mensaje1, n)
        reclamo.agregarMensaje(mensaje2, c)
        expect:"mensajes agregados correctamente al reclamo"
        reclamo != null && reclamo.estado == ReclamoEstado.Respondido && reclamo.negocio == n && reclamo.cliente == c && reclamo.mensajes.size() == 2 && reclamo.mensajes[0].duenio == n && reclamo.mensajes[0].texto == mensaje1 && reclamo.mensajes[1].duenio == c && reclamo.mensajes[1].texto == mensaje2
    }

    void "agregar mensaje del negocio y cerrar el reclamo"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo = crearReclamo(n, c)
        String mensaje = "Primer mensaje del negocio"
        reclamo.agregarMensaje(mensaje, n)
        reclamo.cerrar(c)
        expect:"mensaje agregado correctamente al reclamo y cerrado"
        reclamo != null && reclamo.estado == ReclamoEstado.Cerrado && reclamo.negocio == n && reclamo.cliente == c && reclamo.mensajes.size() == 1 && reclamo.mensajes[0].duenio == n && reclamo.mensajes[0].texto == mensaje
    }

    void "agregar mensaje del negocio, cerrar y reabrir un reclamo"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo = crearReclamo(n, c)
        String mensaje = "Primer mensaje del negocio"
        reclamo.agregarMensaje(mensaje, n)
        reclamo.cerrar(c)
        reclamo.reabrir()
        expect:"mensaje agregado correctamente al reclamo, cerrado y reabierto"
        reclamo != null && reclamo.estado == ReclamoEstado.Abierto && reclamo.negocio == n && reclamo.cliente == c && reclamo.mensajes.size() == 1 && reclamo.mensajes[0].duenio == n && reclamo.mensajes[0].texto == mensaje
    }

    void "cerrar y reabrir sin mensajes"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo = crearReclamo(n, c)
        reclamo.cerrar(c)
        reclamo.reabrir()
        expect:"reclamo cerrado y reabierto correctamente"
        reclamo != null && reclamo.estado == ReclamoEstado.Abierto && reclamo.negocio == n && reclamo.cliente == c && reclamo.mensajes.size() == 0
    }
}
