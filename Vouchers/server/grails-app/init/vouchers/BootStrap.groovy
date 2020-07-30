package vouchers

import grails.gorm.transactions.Transactional


class BootStrap {

    def init = {
        initialData()
    }

    @Transactional
    void initialData() {
        new Country(name: "Argentina").save(failOnError:true)

        new Address(street: "asdffd",
                number: "3",
                apartment:"apartment",
                province: "province",
                country: Country.get(1)).save(failOnError:true)

        Business business = new Business(name:"The Stand",
                phone_number: '123',
                address: Address.get(1),
                category: "food",
                email: "asdf@sadf.com",
                password: "topSecret",
                username: "thestand").save(failOnError:true)

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

        Client client = new Client(fullName: "Pepe Argento",
                phoneNumber: "1234",
                email: "asdf@asdf.com",
                password: "1234",
                username: "pepeargento11").save(failOnError:true)

        Role roleClient = new Role(authority: "ROLE_CLIENT").save(failOnError:true)
        Role roleBusiness = new Role(authority: "ROLE_BUSINESS").save(failOnError:true)

        UserRole.create(client, roleClient, true)
        UserRole.create(business, roleBusiness, true)
    }
    def destroy = {
    }
}
