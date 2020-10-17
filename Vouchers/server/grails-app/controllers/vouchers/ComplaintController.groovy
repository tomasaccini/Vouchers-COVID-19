package vouchers

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class ComplaintController {

    ComplaintService complaintService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond complaintService.list(params), model:[complaintCount: complaintService.count()]
    }

    def show(Long id) {
        respond complaintService.get(id)
    }

    @Transactional
    def save(Complaint complaint) {
        if (complaint == null) {
            render status: NOT_FOUND
            return
        }
        if (complaint.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond complaint.errors
            return
        }

        try {
            complaintService.save(complaint)
        } catch (ValidationException e) {
            respond complaint.errors
            return
        }

        respond complaint, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Complaint complaint) {
        if (complaint == null) {
            render status: NOT_FOUND
            return
        }
        if (complaint.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond complaint.errors
            return
        }

        try {
            complaintService.save(complaint)
        } catch (ValidationException e) {
            respond complaint.errors
            return
        }

        respond complaint, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        complaintService.delete(id)

        render status: NO_CONTENT
    }
}
