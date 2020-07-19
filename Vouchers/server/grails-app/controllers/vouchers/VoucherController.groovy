package vouchers


import grails.rest.*
import grails.converters.*

import static org.springframework.http.HttpStatus.CREATED

class VoucherController extends RestfulController {
    static responseFormats = ['json', 'xml']
    VoucherController() {
        super(Voucher)
    }

    // !!!!
    def create() {
        println "ASDASDsdfs"
        respond new Obj(a: 10)
    }
    // !!!!
    def asd() {
        println "!!!! asd2"
        respond [1, 2, 3]
    }
}

// !!!!
class Obj {
    int a
}
