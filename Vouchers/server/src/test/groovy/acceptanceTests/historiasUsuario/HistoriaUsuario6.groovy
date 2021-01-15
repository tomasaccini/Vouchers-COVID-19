package acceptanceTests.historiasUsuario

import acceptanceTests.steps.Steps
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class HistoriaUsuario6 extends Specification {
    String descripcion = "Como cliente quiero iniciar un reclamo sobre un voucher que compré para poder comunicarme con el negocio y resolver los problemas que puedan surgir."

    void "Escenario 1"() {
        given:
        Steps."Un negocio existe"()
        Steps."Un cliente existe"()
        Steps."Existe un talonario asociado a dicho negocio"()
        Steps."El talonario esta activo"()
        Steps."El cliente compra un voucher del talonario"()
        when:
        Steps."El cliente inicia un reclamo"()
        then:
        Steps."El negocio puede ver el reclamo"()
        Steps."El negocio puede contestar el reclamo"()
        Steps."El cliente puede cerrar el reclamo"()
    }
}
