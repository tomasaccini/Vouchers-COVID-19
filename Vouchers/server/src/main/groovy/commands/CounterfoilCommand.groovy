package commands

import grails.validation.Validateable
import vouchers.Counterfoil
import vouchers.Voucher

class CounterfoilCommand implements Validateable {

    Long id
    Long version
    VoucherInformationCommand voucherInformationCommand
    Integer stock
    List vouchersCommand

    static constraints = {
        importFrom Counterfoil
    }
}
