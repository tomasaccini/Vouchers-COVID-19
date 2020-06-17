package commands

import grails.validation.Validateable
import vouchers.Counterfoil

class CounterfoilCommand implements Validateable {

    Long id
    Long version
    VoucherInformationCommand voucherInformationCommand
    Boolean active
    Integer stock
    List<VoucherCommand> vouchersCommand

    static constraints = {
        importFrom Counterfoil
    }
}
