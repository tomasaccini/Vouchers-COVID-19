package vouchers


import grails.rest.*
import grails.converters.*

class PurchaseController extends RestfulController {
    static responseFormats = ['json', 'xml']
    PurchaseController() {
        super(Purchase)
    }
}
