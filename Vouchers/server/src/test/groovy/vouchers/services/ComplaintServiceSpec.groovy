package vouchers.services

import grails.testing.mixin.integration.Integration
import org.springframework.test.annotation.Rollback
import spock.lang.Specification

@Integration
@Rollback
class ComplaintServiceSpec extends Specification{

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
