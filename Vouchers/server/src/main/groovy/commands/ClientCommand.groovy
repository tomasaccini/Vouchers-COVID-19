package commands

import grails.validation.Validateable
import vouchers.Client
import vouchers.Voucher

class ClientCommand implements Validateable {

    Long id
    String fullName
    String phoneNumber
    List<VoucherCommand> vouchersCommand

    static constraints = {
        importFrom Client
    }
}
