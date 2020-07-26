package vouchers

class BootStrap {

    def init = { servletContext ->
        new Country(name: "Argentina").save(failOnError:true)

        new Address(street: "asdffd",
                    number: "3",
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
    }

    def destroy = {
    }
}
