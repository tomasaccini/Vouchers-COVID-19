package vouchers

import grails.rest.*
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class CounterfoilController extends RestfulController{

    static responseFormats = ['json', 'xml']
    static allowedMethods = [getAll: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    CounterfoilService counterfoilService

    CounterfoilController() {
        super(Counterfoil)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond counterfoilService.list(params), model:[counterfoilCount: counterfoilService.count()]
    }

    def show(Long id) {
        respond counterfoilService.get(id)
    }

    List<Counterfoil> getAll() {
        respond counterfoilService.getAll()
    }

    def create() {
        respond counterfoilService.create(), [status: CREATED]
    }

    @Transactional
    def save(Counterfoil counterfoil) {
        respond counterfoilService.create(), [status: CREATED]

        /*
        if (counterfoil == null) {
            render status: NOT_FOUND
            return
        }
        if (counterfoil.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond counterfoil.errors
            return
        }

        try {
            counterfoilService.save(counterfoil)
        } catch (ValidationException e) {
            respond counterfoil.errors
            return
        }

        respond counterfoil, [status: CREATED, view:"show"]
         */
    }

    @Transactional
    def update(Counterfoil counterfoil) {
        println "!!!! update"
        if (counterfoil == null) {
            render status: NOT_FOUND
            return
        }
        if (counterfoil.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond counterfoil.errors
            return
        }

        try {
            counterfoilService.save(counterfoil)
        } catch (ValidationException e) {
            respond counterfoil.errors
            return
        }

        respond counterfoil, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        counterfoilService.delete(id)

        render status: NO_CONTENT
    }
}
