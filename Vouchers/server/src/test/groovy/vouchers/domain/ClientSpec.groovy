package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Cliente

class ClientSpec extends Specification implements DomainUnitTest<Cliente> {

    def cleanup() {
    }

    void "constructor"() {
        Cliente c = new Cliente(fullName: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        expect:"client constructed correctly"
            c != null && c.fullName == "Ricardo Fort" && c.email == "ricki@gmail.com" && c.contrasenia == "ricki1234" && c.phoneNumber == null && !c.cuentaVerificada
    }

    void "verify account"() {
        Cliente c = new Cliente(fullName: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        c.verify_account()
        expect:"account is verified"
            c.cuentaVerificada
    }
}
