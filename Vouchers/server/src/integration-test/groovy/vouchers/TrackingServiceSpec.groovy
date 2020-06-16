package vouchers

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class TrackingServiceSpec extends Specification {

    TrackingService trackingService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Tracking(...).save(flush: true, failOnError: true)
        //new Tracking(...).save(flush: true, failOnError: true)
        //Tracking tracking = new Tracking(...).save(flush: true, failOnError: true)
        //new Tracking(...).save(flush: true, failOnError: true)
        //new Tracking(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //tracking.id
    }

    void "test get"() {
        setupData()

        expect:
        trackingService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Tracking> trackingList = trackingService.list(max: 2, offset: 2)

        then:
        trackingList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        trackingService.count() == 5
    }

    void "test delete"() {
        Long trackingId = setupData()

        expect:
        trackingService.count() == 5

        when:
        trackingService.delete(trackingId)
        sessionFactory.currentSession.flush()

        then:
        trackingService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Tracking tracking = new Tracking()
        trackingService.save(tracking)

        then:
        tracking.id != null
    }
}
