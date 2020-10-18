package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Direccion
import vouchers.Negocio

class NegocioSpec extends Specification implements DomainUnitTest<Negocio> {

    def cleanup() {
    }

    void "constructor"() {
        Negocio b = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        expect:"business constructed correctly"
        b != null && b.nombre == "Burger" && b.email == "burger@gmail.com" && b.contrasenia == "burger1234" && b.numeroTelefonico == "123412341234" && b.categoria == "Restaurant" && !b.cuentaVerificada
    }

    void "verify account"() {
        Negocio b = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        b.verify_account()
        expect:"account is verified"
        b.cuentaVerificada
    }

}
