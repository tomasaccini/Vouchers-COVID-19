package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Client

class ClientSpec extends Specification implements DomainUnitTest<Client> {

    def cleanup() {
    }

    void "constructor"() {
        Client c = new Client(fullName: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        expect:"client constructed correctly"
            c != null && c.fullName == "Ricardo Fort" && c.email == "ricki@gmail.com" && c.password == "ricki1234" && c.phoneNumber == null && !c.verifiedAccount
    }

    void "verify account"() {
        Client c = new Client(fullName: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        c.verify_account()
        expect:"account is verified"
            c.verifiedAccount
    }
}
