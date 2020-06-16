package commands

import grails.validation.Validateable
import states.VoucherState
import vouchers.Voucher

class VoucherCommand implements Validateable {

    VoucherInformationCommand voucherInformation
    Date dateCreated
    VoucherState state
    Date lastStateChange = new Date()
    List<ItemCommand> items


    static constraints = {
        importFrom Voucher
    }
}
