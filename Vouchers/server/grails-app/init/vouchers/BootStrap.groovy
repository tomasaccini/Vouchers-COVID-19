package vouchers


import enums.ProductType

class BootStrap {

    NegocioService negocioService
    VoucherService voucherService
    ClienteService clienteService
    ReclamoService reclamoService

    def init = { servletContext ->

        new Direccion(calle: "asdffd",
                    numero: "3",
                    departamento:"apartment",
                    provincia: "province",
                    pais: "Argentina").save(failOnError:true)

        new Direccion(calle: "Elcano",
                numero: "311",
                departamento:"apartment",
                provincia: "province",
                pais: "Argentina").save(failOnError:true)

        def negocio1 = new Negocio(nombre:"The Stand",
                    numeroTelefonico: '123',
                    direccion: Direccion.get(1),
                    categoria: "food",
                    email: "negocionombrelargo@negocio.com",
                    contrasenia: "topSecret",
                    cuentaVerificada: Boolean.TRUE).save(failOnError:true)

        def negocio2 = new Negocio(nombre:"Mc Dollar",
                numeroTelefonico: '123',
                direccion: Direccion.get(2),
                categoria: "food",
                email: "negocio2@sadf.com",
                contrasenia: "topSecret",
                cuentaVerificada: Boolean.TRUE).save(failOnError:true)

        def informacionVoucher1 = new InformacionVoucher(precio: 3.0,
                descripcion: "Rico",
                validoDesde: new Date(),
                validoHasta: new Date()).save(failOnError:true)

        def informacionVoucher2 = new InformacionVoucher(precio: 3.0,
                descripcion: "Feo",
                validoDesde: new Date(),
                validoHasta: new Date()).save(failOnError:true)

        def talonario1 = new Talonario(stock: 5,
                        informacionVoucher: InformacionVoucher.get(1),
                        negocio: Negocio.get(1)
                        ).save(failOnError:true)

        def talonario2 = new Talonario(stock: 5,
                informacionVoucher: informacionVoucher2,
                negocio: negocio2).save(failOnError:true)

        negocio1.addToTalonarios(talonario1)
        negocio2.addToTalonarios(talonario2)

        def cliente1 = new Cliente(fullName: "Pepe Argento",
                phoneNumber: "1234",
                email: "cliente@asdf.com",
                contrasenia: "1234",
                cuentaVerificada: Boolean.TRUE).save(failOnError:true)

        new Producto(nombre: "Medialunas",
                descripcion: "veganas",
                negocio: Negocio.get(1),
                type: ProductType.FAST_FOOD).save(failOnError:true)

        negocio1.addToProducts(Producto.get(1))

        new Producto(nombre: "Hamburgüesas",
                descripcion: "veganas",
                negocio: Negocio.get(2),
                type: ProductType.FAST_FOOD).save(failOnError:true)

        def item1 = new Item(
                cantidad: 5,
                producto: Producto.get(1)).save(failOnError:true)

        def item2 = new Item(
                cantidad: 5,
                producto: Producto.get(2)).save(failOnError:true)


        Negocio.get(2).addToProducts(Producto.get(2))

        talonario1.informacionVoucher.addToItems(item1)
        talonario1.informacionVoucher.addToItems(item2)

        // Vouchers sin reclamar
        def voucher11 = clienteService.comprarVoucher(cliente1.id, talonario1.id)
        def voucher12 = clienteService.comprarVoucher(cliente1.id, talonario1.id)
        def voucher21 = clienteService.comprarVoucher(cliente1.id, talonario2.id)

        // Vouchers reclamados
        def voucher13 = clienteService.comprarVoucher(cliente1.id, talonario1.id)
        def voucher14 = clienteService.comprarVoucher(cliente1.id, talonario1.id)
        def voucher22 = clienteService.comprarVoucher(cliente1.id, talonario2.id)

        def reclamo13 = reclamoService.iniciarReclamo(voucher13.id, "Initial message !!!!")
        // reclamoService.nuevoMensaje(reclamo11.id, talonario1.negocio.id, "Respuesta del negocio")

        def reclamo14 = reclamoService.iniciarReclamo(voucher14.id, "Initial message 2 !!!!")
        def reclamo22 = reclamoService.iniciarReclamo(voucher22.id, "Initial message 3 !!!!")
    }

    def destroy = {
    }
}
