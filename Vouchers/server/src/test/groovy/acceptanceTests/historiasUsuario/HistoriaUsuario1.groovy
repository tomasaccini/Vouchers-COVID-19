package acceptanceTests.historiasUsuario

import acceptanceTests.steps.Steps
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class HistoriaUsuario1 extends Specification {
    String descripcion = "Como negocio quiero poder activar un talonario para que el mismo pueda ser comprado por los clientes."

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
