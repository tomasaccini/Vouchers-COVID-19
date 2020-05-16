package vouchers


import grails.rest.*
import grails.converters.*

class BusinessController extends RestfulController {
    static responseFormats = ['json', 'xml']
    BusinessController() {
        super(Business)
    }
}
