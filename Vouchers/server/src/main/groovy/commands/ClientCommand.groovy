package commands

import grails.validation.Validateable
import vouchers.Client
import vouchers.Voucher

class ClientCommand implements Validateable {

    String fullName
    String phoneNumber
    List vouchersCommand

    static constraints = {
        importFrom Client
    }
}
