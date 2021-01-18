package acceptanceTests.historiasUsuario

import acceptanceTests.steps.Steps
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class HistoriaUsuario4 extends Specification {
    String descripcion = "Como cliente quiero solicitar un canje de vouchers luego de ser comprados para obtener los servicios o productos prometidos."

    void "Escenario 1"() {
        given:
        Steps."Un negocio existe"()
        Steps."Un cliente existe"()
        Steps."Existe un talonario asociado a dicho negocio"()
        Steps."El talonario esta activo"()
        Steps."El cliente compra un voucher del talonario"()
        when:
        Steps."El cliente solicita canjear el voucher"()
        then:
        Steps."El voucher se marca como pendiente de confirmacion"()
    }

    void "Escenario 2"() {
        given:
        Steps."Un negocio existe"()
        Steps."Un cliente existe"()
        Steps."Existe un talonario asociado a dicho negocio"()
        Steps."El talonario esta activo"()
        Steps."El cliente compra un voucher del talonario"()
        Steps."El cliente solicita canjear el voucher"()
        when:
        Steps."El cliente solicita canjear el voucher"()
        then:
        Steps."El cliente no puede volver a solicitar el canje porque ya esta pendiente de confirmacion"()
    }
}
