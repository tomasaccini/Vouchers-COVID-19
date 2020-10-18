package vouchers

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ReclamoServiceSpec extends Specification {

    ReclamoService complaintService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Complaint(...).save(flush: true, failOnError: true)
        //new Complaint(...).save(flush: true, failOnError: true)
        //Complaint complaint = new Complaint(...).save(flush: true, failOnError: true)
        //new Complaint(...).save(flush: true, failOnError: true)
        //new Complaint(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //complaint.id
    }

    void "test get"() {
        setupData()

        expect:
        complaintService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Reclamo> complaintList = complaintService.list(max: 2, offset: 2)

        then:
        complaintList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        complaintService.count() == 5
    }

    void "test delete"() {
        Long complaintId = setupData()

        expect:
        complaintService.count() == 5

        when:
        complaintService.delete(complaintId)
        sessionFactory.currentSession.flush()

        then:
        complaintService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Reclamo complaint = new Reclamo()
        complaintService.save(complaint)

        then:
        complaint.id != null
    }
}