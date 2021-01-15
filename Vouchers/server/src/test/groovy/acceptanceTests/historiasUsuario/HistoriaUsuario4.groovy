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
    String descripcion = "Como cliente quiero canjear vouchers luego de ser comprados para obtener los servicios o productos prometidos."

    void "Escenario 1"() {
        given:
        Steps."Un negocio existe"()
        Steps."Un cliente existe"()
        Steps."Existe un talonario asociado a dicho negocio"()
        Steps."El talonario esta activo"()
        Steps."El cliente compro un voucher del talonario previamente"()
        when:
        Steps."El cliente solicita canjear el voucher"()
        Steps."El negocio confirma el canje"()
        then:
        Steps."El cliente recibe el servicio o producto"()
        Steps."El voucher se marca como canjeado"()
    }

    void "Escenario 2"() {
        given:
        Steps."Un negocio existe"()
        Steps."Un cliente existe"()
        Steps."Existe un talonario asociado a dicho negocio"()
        Steps."El talonario esta activo"()
        Steps."El cliente compro un voucher del talonario previamente"()
        when:
        Steps."El cliente solicita canjear el voucher"()
        Steps."El negocio confirma el canje"()
        Steps."El cliente solicita canjear el voucher"()
        then:
        Steps."El cliente no recibe el producto ni servicio porque ya fue canjeado"()
    }
}
