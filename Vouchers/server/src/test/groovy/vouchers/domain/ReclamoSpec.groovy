package vouchers.domain

import enums.states.ReclamoEstado
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.*

class ReclamoSpec extends Specification implements DomainUnitTest<Reclamo> {

    def crearInformacionVoucher(validoHasta = new Date('2030/12/31')) {
        Producto p1 = new Producto(nombre: "Hamburguesa", descripcion: "Doble cheddar")
        Producto p2 = new Producto(nombre: "Pinta cerveza", descripcion: "Cerveza artesanal de la casa")
        Item i1 = new Item(producto: p1, cantidad: 1)
        Item i2 = new Item(producto: p2, cantidad: 2)

        InformacionVoucher iv = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: validoHasta, items: [i1, i2])
        iv
    }

    def crearReclamo(Negocio n, Cliente c) {
        InformacionVoucher iv = crearInformacionVoucher()
        Talonario talonario = new Talonario(informacionVoucher: iv, stock: 3, activo: true)
        n.addToTalonarios(talonario)
        Voucher v = talonario.comprarVoucher(c)
        Reclamo reclamo = new Reclamo(descripcion: "Descripcion de mi problema", voucher: v, fechaCreacion: new Date())
        v.reclamo = reclamo
        reclamo
    }

    void "constructor"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo = crearReclamo(n, c)
        expect: "reclamo construido correctamente"
        reclamo != null && reclamo.estado == ReclamoEstado.Abierto && reclamo.getVoucher().getTalonario().getNegocio() == n && reclamo.getVoucher().getCliente() == c && reclamo.mensajes.size() == 0
    }

    void "agregar mensaje del negocio"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo = crearReclamo(n, c)
        String mensaje = "Primer mensaje del negocio"
        reclamo.agregarMensaje(mensaje, n)
        expect: "mensaje agregado correctamente al reclamo"
        reclamo != null && reclamo.estado == ReclamoEstado.Respondido && reclamo.getVoucher().getTalonario().getNegocio() == n && reclamo.getVoucher().getCliente() == c && reclamo.mensajes.size() == 1 && reclamo.mensajes[0].duenio == n && reclamo.mensajes[0].texto == mensaje
    }

    void "agregar mensaje del cliente"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo = crearReclamo(n, c)
        String mensaje = "Primer mensaje del cliente"
        reclamo.agregarMensaje(mensaje, c)
        expect: "mensaje agregado correctamente al reclamo"
        reclamo != null && reclamo.estado == ReclamoEstado.Abierto && reclamo.getVoucher().getTalonario().getNegocio() == n && reclamo.getVoucher().getCliente() == c && reclamo.mensajes.size() == 1 && reclamo.mensajes[0].duenio == c && reclamo.mensajes[0].texto == mensaje
    }

    void "agregar dos mensajes"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo = crearReclamo(n, c)
        String mensaje1 = "Primer mensaje del negocio"
        String mensaje2 = "Segundo mensaje del cliente"
        reclamo.agregarMensaje(mensaje1, n)
        reclamo.agregarMensaje(mensaje2, c)
        expect: "mensajes agregados correctamente al reclamo"
        reclamo != null && reclamo.estado == ReclamoEstado.Respondido && reclamo.getVoucher().getTalonario().getNegocio() == n && reclamo.getVoucher().getCliente() == c && reclamo.mensajes.size() == 2 && reclamo.mensajes[0].duenio == n && reclamo.mensajes[0].texto == mensaje1 && reclamo.mensajes[1].duenio == c && reclamo.mensajes[1].texto == mensaje2
    }

    void "agregar mensaje del negocio y cerrar el reclamo"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo = crearReclamo(n, c)
        String mensaje = "Primer mensaje del negocio"
        reclamo.agregarMensaje(mensaje, n)
        reclamo.cerrar(c)
        expect: "mensaje agregado correctamente al reclamo y cerrado"
        reclamo != null && reclamo.estado == ReclamoEstado.Cerrado && reclamo.getVoucher().getTalonario().getNegocio() == n && reclamo.getVoucher().getCliente() == c && reclamo.mensajes.size() == 1 && reclamo.mensajes[0].duenio == n && reclamo.mensajes[0].texto == mensaje
    }

    void "agregar mensaje del negocio, cerrar y reabrir un reclamo"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo = crearReclamo(n, c)
        String mensaje = "Primer mensaje del negocio"
        reclamo.agregarMensaje(mensaje, n)
        reclamo.cerrar(c)
        reclamo.reabrir()
        expect: "mensaje agregado correctamente al reclamo, cerrado y reabierto"
        reclamo != null && reclamo.estado == ReclamoEstado.Abierto && reclamo.getVoucher().getTalonario().getNegocio() == n && reclamo.getVoucher().getCliente() == c && reclamo.mensajes.size() == 1 && reclamo.mensajes[0].duenio == n && reclamo.mensajes[0].texto == mensaje
    }

    void "cerrar y reabrir sin mensajes"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo = crearReclamo(n, c)
        reclamo.cerrar(c)
        reclamo.reabrir()
        expect: "reclamo cerrado y reabierto correctamente"
        reclamo != null && reclamo.estado == ReclamoEstado.Abierto && reclamo.getVoucher().getTalonario().getNegocio() == n && reclamo.getVoucher().getCliente() == c && reclamo.mensajes.size() == 0
    }

    void "obtener reclamos abiertos: un reclamo cerrado devuelve una lista vacia"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo1 = crearReclamo(n, c)
        reclamo1.cerrar(c)
        expect: "obtiene una lista vacia"
        n.obtenerReclamosAbiertos().size() == 0
    }

    void "obtener reclamos abiertos: un reclamo abierto devuelve una lista con ese reclamo"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo1 = crearReclamo(n, c)
        expect: "obtiene una lista con un elemento"
        n.obtenerReclamosAbiertos().size() == 1 && n.obtenerReclamosAbiertos()[0] == reclamo1
    }

    void "obtener reclamos abiertos: un reclamo abierto y uno cerrado devuelve una lista con el reclamo abierto"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo1 = crearReclamo(n, c)
        Reclamo reclamo2 = crearReclamo(n, c)
        reclamo1.cerrar(c)
        expect: "obtiene una lista con un elemento"
        n.obtenerReclamosAbiertos().size() == 1 && n.obtenerReclamosAbiertos()[0] == reclamo2
    }

    void "obtener reclamos abiertos: dos reclamo abiertos devuleve una lista con los dos reclamos abiertos"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        Reclamo reclamo1 = crearReclamo(n, c)
        Reclamo reclamo2 = crearReclamo(n, c)
        expect: "obtiene una lista con dos elementos"
        n.obtenerReclamosAbiertos().size() == 2 && n.obtenerReclamosAbiertos().contains(reclamo1) && n.obtenerReclamosAbiertos().contains(reclamo2)
    }
}
