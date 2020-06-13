package vouchers

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ClientSpec extends Specification implements DomainUnitTest<Client> {

    def setup() {
    }

    def cleanup() {
    }

    void "constructor"() {
        Client c = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        expect:"client constructed correctly"
            c != null && c.full_name == "Ricardo Fort" && c.email == "ricki@gmail.com" && c.password == "ricki1234" && c.phone_number == null && !c.verified_account
    }

    void "verify account"() {
        Client c = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        c.verify_account()
        expect:"account is verified"
            c.verified_account
    }
}
