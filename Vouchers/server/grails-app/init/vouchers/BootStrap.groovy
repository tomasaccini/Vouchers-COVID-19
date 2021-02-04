package vouchers

import enums.ProductoTipo
import groovy.time.TimeCategory

class BootStrap {

    NegocioService negocioService
    VoucherService voucherService
    ClienteService clienteService
    ReclamoService reclamoService

    def init = { servletContext ->

        new Direccion(calle: "asdffd",
                numero: "3",
                departamento: "apartment",
                provincia: "province",
                pais: "Argentina").save(failOnError: true)

        new Direccion(calle: "Elcano",
                numero: "311",
                departamento: "apartment",
                provincia: "province",
                pais: "Argentina").save(failOnError: true)

        def negocio1 = new Negocio(nombre: "The Stand",
                numeroTelefonico: '123',
                direccion: Direccion.get(1),
                categoria: "food",
                email: "negocionombrelargo@negocio.com",
                contrasenia: "topSecret",
                cuentaVerificada: Boolean.TRUE).save(failOnError: true)

        def negocio2 = new Negocio(nombre: "Mc Dollar",
                numeroTelefonico: '123',
                direccion: Direccion.get(2),
                categoria: "food",
                email: "negocio2@sadf.com",
                contrasenia: "topSecret",
                cuentaVerificada: Boolean.TRUE).save(failOnError: true)

        def informacionVoucher1
        def informacionVoucher2
        use(TimeCategory) {
            informacionVoucher1 = new InformacionVoucher(precio: 3.0,
                    descripcion: "Rico",
                    validoDesde: new Date() - 1.year,
                    validoHasta: new Date() + 1.year).save(failOnError: true)

            informacionVoucher2 = new InformacionVoucher(precio: 3.0,
                    descripcion: "Feo",
                    validoDesde: new Date() - 1.year,
                    validoHasta: new Date() + 1.year).save(failOnError: true)
        }

        def talonario1 = new Talonario(stock: 6,
                informacionVoucher: InformacionVoucher.get(1),
                negocio: Negocio.get(1),
                activo: true).save(failOnError: true)

        def talonario2 = new Talonario(stock: 15,
                informacionVoucher: informacionVoucher2,
                negocio: negocio2,
                activo: true).save(failOnError: true)

        negocio1.addToTalonarios(talonario1)
        negocio2.addToTalonarios(talonario2)

        def cliente1 = new Cliente(nombreCompleto: "Pepe Argento",
                numeroTelefonico: "1234",
                email: "cliente@asdf.com",
                contrasenia: "1234",
                cuentaVerificada: Boolean.TRUE).save(failOnError: true)

        def cliente2 = new Cliente(nombreCompleto: "Moni Argento",
                numeroTelefonico: "1234",
                email: "noeselmismocliente@asdf.com",
                contrasenia: "1234",
                cuentaVerificada: Boolean.TRUE).save(failOnError: true)

        new Producto(nombre: "Medialunas",
                descripcion: "veganas",
                negocio: Negocio.get(1),
                tipo: ProductoTipo.COMIDA_RAPIDA).save(failOnError: true)

        negocio1.addToProductos(Producto.get(1))

        new Producto(nombre: "Hamburgüesas",
                descripcion: "veganas",
                negocio: Negocio.get(2),
                tipo: ProductoTipo.COMIDA_RAPIDA).save(failOnError: true)

        def item1 = new Item(
                cantidad: 5,
                producto: Producto.get(1)).save(failOnError: true)

        def item2 = new Item(
                cantidad: 5,
                producto: Producto.get(2)).save(failOnError: true)


        Negocio.get(2).addToProductos(Producto.get(2))

        talonario1.informacionVoucher.addToItems(item1)
        talonario1.informacionVoucher.addToItems(item2)

        // Vouchers comprados
        def voucher11 = clienteService.comprarVoucher(cliente1.id, talonario1.id)
        def voucher12 = clienteService.comprarVoucher(cliente1.id, talonario1.id)
        def voucher21 = clienteService.comprarVoucher(cliente1.id, talonario2.id)

        // Vouchers reclamados
        def voucher13 = clienteService.comprarVoucher(cliente1.id, talonario1.id)
        def voucher14 = clienteService.comprarVoucher(cliente1.id, talonario1.id)
        def voucher22 = clienteService.comprarVoucher(cliente1.id, talonario2.id)

        def reclamo13 = reclamoService.abrirReclamo(voucher13.id, "Initial message")
        // reclamoService.nuevoMensaje(reclamo11.id, talonario1.negocio.id, "Respuesta del negocio")

        def reclamo14 = reclamoService.abrirReclamo(voucher14.id, "Initial message 2")
        def reclamo22 = reclamoService.abrirReclamo(voucher22.id, "Initial message 3")

        // Vouchers confirmacion pendiente
        def voucher15 = clienteService.comprarVoucher(cliente1.id, talonario1.id)
        voucherService.solicitarCanje(voucher15.id, voucher15.cliente.id)

        // Vouchers canjeados
        def voucher23 = clienteService.comprarVoucher(cliente1.id, talonario2.id)
        voucherService.solicitarCanje(voucher23.id, voucher23.cliente.id)
        voucherService.confirmarCanje(voucher23.id, voucher23.talonario.negocio.id)

        def voucher24 = clienteService.comprarVoucher(cliente1.id, talonario2.id)
        voucherService.solicitarCanje(voucher24.id, voucher24.cliente.id)
        voucherService.confirmarCanje(voucher24.id, voucher24.talonario.negocio.id)

        def voucher25 = clienteService.comprarVoucher(cliente1.id, talonario2.id)
        voucherService.solicitarCanje(voucher25.id, voucher25.cliente.id)
        voucherService.confirmarCanje(voucher25.id, voucher25.talonario.negocio.id)

        def voucher26 = clienteService.comprarVoucher(cliente1.id, talonario2.id)
        voucherService.solicitarCanje(voucher26.id, voucher26.cliente.id)
        voucherService.confirmarCanje(voucher26.id, voucher26.talonario.negocio.id)
    }

    def destroy = {
    }
}
