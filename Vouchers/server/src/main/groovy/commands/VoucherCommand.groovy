package commands

import grails.validation.Validateable
import enums.states.VoucherState
import vouchers.Voucher

class VoucherCommand implements Validateable {

    VoucherInformationCommand voucherInformationCommand
    Date dateCreated
    VoucherState state
    Date lastStateChange = new Date()

    static constraints = {
        importFrom Voucher
    }
}
