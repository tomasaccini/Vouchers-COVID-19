package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Address
import vouchers.Business
import vouchers.Country

class BusinessSpec extends Specification implements DomainUnitTest<Business> {

    def cleanup() {
    }

    void "constructor"() {
        Business b = new Business(name: "Burger", phone_number: "123412341234", address: new Address(street: "Libertador", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "burger@gmail.com", password: "burger1234")
        expect:"business constructed correctly"
        b != null && b.name == "Burger" && b.email == "burger@gmail.com" && b.password == "burger1234" && b.phone_number == "123412341234" && b.category == "Restaurant"
    }
}
