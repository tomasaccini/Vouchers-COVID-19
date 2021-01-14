package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Cliente

class ClienteSpec extends Specification implements DomainUnitTest<Cliente> {

    def cleanup() {
    }

    void "constructor"() {
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        expect: "cliente construido correctamente"
        c != null && c.nombreCompleto == "Ricardo Fort" && c.email == "ricki@gmail.com" && c.contrasenia == "ricki1234" && c.numeroTelefonico == null && !c.cuentaVerificada
    }

    void "verificar cuenta"() {
        Cliente c = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        c.verificarCuenta()
        expect: "la cuenta esta verificada"
        c.cuentaVerificada
    }
}
