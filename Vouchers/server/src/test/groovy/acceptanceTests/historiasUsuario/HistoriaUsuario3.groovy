package acceptanceTests.historiasUsuario

import acceptanceTests.steps.Steps
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class HistoriaUsuario3 extends Specification {
    String descripcion = "Como cliente quiero comprar vouchers para poder canjearlos en un futuro."

    void "Escenario 1"() {
        given:
        Steps."Un negocio existe"()
        Steps."Un cliente existe"()
        Steps."Existe un talonario asociado a dicho negocio"()
        Steps."El talonario esta activo"()
        when:
        Steps."Un cliente compra un voucher del talonario"()
        then:
        Steps."La transaccion es exitosa"()
        Steps."El voucher se guarda en la sección Mis vouchers del cliente"()
    }
}
