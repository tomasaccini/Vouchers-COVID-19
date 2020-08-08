package vouchers

import enums.ProductType

class BootStrap {

    def init = { servletContext ->
        new Country(name: "Argentina").save(failOnError:true)

        new Address(street: "asdffd",
                    number: "3",
                    apartment:"apartment",
                    province: "province",
                    country: Country.get(1)).save(failOnError:true)

        new Address(street: "Elcano",
                number: "311",
                apartment:"apartment",
                province: "province",
                country: Country.get(1)).save(failOnError:true)

        new Business(name:"The Stand",
                    phone_number: '123',
                    address: Address.get(1),
                    category: "food",
                    email: "asdf@sadf.com",
                    password: "topSecret",
                    verifiedAccount: Boolean.TRUE).save(failOnError:true)

        new Business(name:"Mc Dollar",
                phone_number: '123',
                address: Address.get(2),
                category: "food",
                email: "asdf@sadf.com",
                password: "topSecret",
                verifiedAccount: Boolean.TRUE).save(failOnError:true)

        new VoucherInformation(price: 3.0,
                    description: "Rico",
                    validFrom: new Date(),
                    validUntil: new Date()).save(failOnError:true)

        new Counterfoil(stock: 10,
                        voucherInformation: VoucherInformation.get(1),
                        business: Business.get(1)
                        ).save(failOnError:true)

        Business.get(1).addToCounterfoils(Counterfoil.get(1))

        new Country(name: "Brazil").save(failOnError:true)

        new Client(fullName: "Pepe Argento",
                phoneNumber: "1234",
                email: "asdf@asdf.com",
                password: "1234",
                verifiedAccount: Boolean.TRUE).save(failOnError:true)

        new Product(name: "Medialunas",
                description: "veganas",
                business: Business.get(1),
                type: ProductType.FAST_FOOD).save(failOnError:true)

        Business.get(1).addToProducts(Product.get(1))

        new Product(name: "Hamburguuesas",
                description: "veganas",
                business: Business.get(2),
                type: ProductType.FAST_FOOD).save(failOnError:true)

        Business.get(2).addToProducts(Product.get(2))
    }

    def destroy = {
    }
}
