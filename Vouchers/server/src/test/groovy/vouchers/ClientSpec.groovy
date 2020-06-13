package vouchers

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ClientSpec extends Specification implements DomainUnitTest<Client> {

    def setup() {
    }

    def cleanup() {
    }

    void "constructor"() {
        Client c = new Client()
        expect:"not null"
            c != null
    }
}
