package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Direccion
import vouchers.Negocio

class NegocioSpec extends Specification implements DomainUnitTest<Negocio> {

    def cleanup() {
    }

    void "constructor"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        expect:"negocio construido correctamente"
        n != null && n.nombre == "Burger" && n.email == "burger@gmail.com" && n.contrasenia == "burger1234" && n.numeroTelefonico == "123412341234" && n.categoria == "Restaurant" && !n.cuentaVerificada
    }

    void "verificar cuenta"() {
        Negocio n = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234")
        n.verificarCuenta()
        expect:"la cuenta esta verificada"
        n.cuentaVerificada
    }

}
