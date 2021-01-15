package acceptanceTests.historiasUsuario

import grails.gorm.transactions.Rollback
import grails.testing.gorm.DataTest
import grails.testing.mixin.integration.Integration
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import acceptanceTests.steps.Steps

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class HistoriaUsuario1 extends Specification {

    void "Escenario 1"() {
        given:
        Steps."Un negocio existe"()
        Steps."Un cliente existe"()
        Steps."Existe un talonario asociado a dicho negocio"()
        when:
        Steps."El negocio activa dicho talonario"()
        then:
        Steps."El talonario es público"()
        Steps."Los clientes pueden buscarlo"()
        Steps."Los clientes pueden comprarlo"()
    }
}
