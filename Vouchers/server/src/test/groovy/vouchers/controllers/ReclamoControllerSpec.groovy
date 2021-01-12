package vouchers.controllers

import spock.lang.*
import vouchers.Reclamo
import vouchers.ReclamoController

import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import grails.validation.ValidationException
import grails.testing.web.controllers.ControllerUnitTest
import grails.testing.gorm.DomainUnitTest

class ReclamoControllerSpec extends Specification implements ControllerUnitTest<ReclamoController>, DomainUnitTest<Reclamo> {

    void "Test the index action returns the correct response"() {
        /*given:
        controller.reclamoService = Mock(ReclamoService) {
            1 * obtener() >> null
        }*/

        when:"Solicito un reclamo con id null"
            controller.obtenerReclamo(null)

        then:"La respuesta es 404"
            response.status == NOT_FOUND.value()
    }

}