package vouchers

import enums.ProductType

class BootStrap {

    def init = { servletContext ->
        new Pais(name: "Argentina").save(failOnError:true)

        new Direccion(calle: "asdffd",
                    numero: "3",
                    departamento:"apartment",
                    provincia: "province",
                    pais: Pais.get(1)).save(failOnError:true)

        new Direccion(calle: "Elcano",
                numero: "311",
                departamento:"apartment",
                provincia: "province",
                pais: Pais.get(1)).save(failOnError:true)

        new Negocio(nombre:"The Stand",
                    numeroTelefonico: '123',
                    direccion: Direccion.get(1),
                    categoria: "food",
                    email: "asdf@sadf.com",
                    contrasenia: "topSecret",
                    cuentaVerificada: Boolean.TRUE).save(failOnError:true)

        new Negocio(nombre:"Mc Dollar",
                numeroTelefonico: '123',
                direccion: Direccion.get(2),
                categoria: "food",
                email: "asdf@sadf.com",
                contrasenia: "topSecret",
                cuentaVerificada: Boolean.TRUE).save(failOnError:true)

        new InformacionVoucher(precio: 3.0,
                    descripcion: "Rico",
                    validoDesde: new Date(),
                    validoHasta: new Date()).save(failOnError:true)

        new Tarifario(stock: 10,
                        informacionVoucher: InformacionVoucher.get(1),
                        negocio: Negocio.get(1)
                        ).save(failOnError:true)

        Negocio.get(1).addToTarifarios(Tarifario.get(1))

        new Pais(name: "Brazil").save(failOnError:true)

        new Cliente(fullName: "Pepe Argento",
                phoneNumber: "1234",
                email: "asdf@asdf.com",
                contrasenia: "1234",
                cuentaVerificada: Boolean.TRUE).save(failOnError:true)

        new Producto(nombre: "Medialunas",
                descripcion: "veganas",
                business: Negocio.get(1),
                type: ProductType.FAST_FOOD).save(failOnError:true)

        Negocio.get(1).addToProducts(Producto.get(1))

        new Producto(nombre: "Hamburguuesas",
                descripcion: "veganas",
                business: Negocio.get(2),
                type: ProductType.FAST_FOOD).save(failOnError:true)

        Negocio.get(2).addToProducts(Producto.get(2))
    }

    def destroy = {
    }
}
