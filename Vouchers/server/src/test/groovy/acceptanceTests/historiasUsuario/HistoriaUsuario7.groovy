package acceptanceTests.historiasUsuario

import acceptanceTests.steps.Steps
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import vouchers.Cliente
import vouchers.Direccion
import vouchers.Negocio
import vouchers.Talonario
import vouchers.Voucher

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class HistoriaUsuario7 extends Specification {
    String descripcion = "Como cliente quiero que la aplicación me recomiende talonarios en base a el rating que tienen y la cantidad de vouchers vendidos para poder visualizar primero los más relevantes."

    def setup() {
        Voucher.findAll().each { it.delete(flush:true, failOnError:true) }
        Talonario.findAll().each { it.delete(flush:true, failOnError:true) }
    }

    void "Escenario 1"() {
        given:
        Steps."Un negocio existe"()
        Steps."Un cliente existe"()
        Steps."Existe un talonario talonario_A asociado a dicho negocio"()
        Steps."El talonario talonario_A esta activo"()
        Steps."Existe un talonario talonario_B asociado a dicho negocio"()
        Steps."El talonario talonario_B esta activo"()
        Steps."El talonario talonario_A tiene 1 voucher vendido"()
        Steps."El talonario talonario_B tiene 1 voucher vendido"()
        Steps."El talonario talonario_A tiene un rating promedio de 5 estrellas"()
        Steps."El talonario talonario_B tiene un rating promedio de 1 estrellas"()
        when:
        Steps."El cliente busca los talonarios activos"()
        then:
        Steps."El talonario talonario_A aparece en la posición 1"()
        Steps."El talonario talonario_B aparece en la posición 2"()
    }
}
