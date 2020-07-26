package vouchers

class BootStrap {

    def init = { servletContext ->
        new Country(name: "Argentina").save()
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
        new Country(name: "Brazil").save()
    }

    def destroy = {
    }
}
