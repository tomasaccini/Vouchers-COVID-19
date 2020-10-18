package commands

import grails.validation.Validateable
import vouchers.InformacionVoucher

class InformacionVoucherCommand implements Validateable{

    Long id
    Long version
    Double price
    String description
    Date validFrom
    Date validUntil
    List itemsCommand

    static constraints = {
        importFrom InformacionVoucher
    }
}
