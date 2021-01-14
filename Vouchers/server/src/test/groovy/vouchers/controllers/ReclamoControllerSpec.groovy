package vouchers.controllers

import grails.testing.gorm.DomainUnitTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification
import vouchers.Reclamo
import vouchers.ReclamoController

import static org.springframework.http.HttpStatus.NOT_FOUND

class ReclamoControllerSpec extends Specification implements ControllerUnitTest<ReclamoController>, DomainUnitTest<Reclamo> {

    void "Test the index action returns the correct response"() {
        /*given:
        controller.reclamoService = Mock(ReclamoService) {
            1 * obtener() >> null
        }*/

        when: "Solicito un reclamo con id null"
        controller.obtenerReclamo(null)

        then: "La respuesta es 404"
        response.status == NOT_FOUND.value()
    }

}