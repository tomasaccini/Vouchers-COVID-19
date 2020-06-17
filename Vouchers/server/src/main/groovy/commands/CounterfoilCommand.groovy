package commands

import grails.validation.Validateable
import vouchers.Counterfoil
import vouchers.Voucher

class CounterfoilCommand implements Validateable {

    Long id
    Long version
    VoucherInformationCommand voucherInformationCommand
    Boolean active
    Integer stock
    List<VoucherCommand> voucher

    static constraints = {
        importFrom Counterfoil
    }
}
