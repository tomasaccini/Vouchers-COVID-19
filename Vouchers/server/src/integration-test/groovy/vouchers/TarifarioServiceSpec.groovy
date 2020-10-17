package vouchers

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class TarifarioServiceSpec extends Specification {

    TarifarioService counterfoilService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Counterfoil(...).save(flush: true, failOnError: true)
        //new Counterfoil(...).save(flush: true, failOnError: true)
        //Counterfoil counterfoil = new Counterfoil(...).save(flush: true, failOnError: true)
        //new Counterfoil(...).save(flush: true, failOnError: true)
        //new Counterfoil(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //counterfoil.id
    }

    void "test get"() {
        setupData()

        expect:
        counterfoilService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Tarifario> counterfoilList = counterfoilService.list(max: 2, offset: 2)

        then:
        counterfoilList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        counterfoilService.count() == 5
    }

    void "test delete"() {
        Long counterfoilId = setupData()

        expect:
        counterfoilService.count() == 5

        when:
        counterfoilService.delete(counterfoilId)
        sessionFactory.currentSession.flush()

        then:
        counterfoilService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Tarifario counterfoil = new Tarifario()
        counterfoilService.save(counterfoil)

        then:
        counterfoil.id != null
    }
}
