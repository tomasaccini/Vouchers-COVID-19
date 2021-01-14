package vouchers.controllers

import grails.testing.gorm.DomainUnitTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification
import vouchers.Talonario
import vouchers.TalonarioController
import vouchers.TalonarioService

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.NOT_FOUND

class TalonarioControllerSpec extends Specification implements ControllerUnitTest<TalonarioController>, DomainUnitTest<Talonario> {

    void "Cuando no hay talonarios se devuelve una lista vacia"() {
        given:
        controller.talonarioService = Mock(TalonarioService) {
            1 * list(_) >> []
        }

        when: "The index action is executed"
        controller.index()

        then: "The response is correct"
        response.text == '[]'
    }


    void "No se puede crear un talonario sin información"() {
        when:
        request.contentType = JSON_CONTENT_TYPE
        request.method = 'POST'
        controller.crear()

        then:
        response.status == BAD_REQUEST.value()
    }

    void "Solicitar un talonario con id no válido devuelve 404"() {
        given:
        controller.talonarioService = Mock(TalonarioService) {
            1 * get(null) >> null
        }

        when: "Cuando se solicita un talonario con id nulo"
        controller.show()

        then: "Un error 404 error es retornado"
        response.status == NOT_FOUND.value()
    }

}
