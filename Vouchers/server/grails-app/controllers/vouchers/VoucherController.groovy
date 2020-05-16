package vouchers


import grails.rest.*
import grails.converters.*

class VoucherController extends RestfulController {
    static responseFormats = ['json', 'xml']
    VoucherController() {
        super(Voucher)
    }
}
