package acceptanceTests.historiasUsuario

import acceptanceTests.steps.Steps
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class HistoriaUsuario2 extends Specification {
    String descripcion = "Como negocio quiero poder desactivar un talonario para que el mismo no pueda ser comprado por los clientes."

    void "Escenario 1"() {
        given:
        Steps."Un negocio existe"()
        Steps."Un cliente existe"()
        Steps."Existe un talonario asociado a dicho negocio"()
        Steps."El talonario esta activo"()
        when:
        Steps."El negocio desactiva dicho talonario"()
        then:
        Steps."El talonario es privado"()
        Steps."Los clientes no pueden buscarlo"()
        Steps."Los clientes no pueden comprarlo"()
    }
}
