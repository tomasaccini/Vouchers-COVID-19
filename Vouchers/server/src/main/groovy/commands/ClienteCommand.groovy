package commands

import grails.validation.Validateable
import vouchers.Cliente

class ClienteCommand implements Validateable {

    Long id
    String fullName
    String phoneNumber
    List<VoucherCommand> vouchersCommand

    static constraints = {
        importFrom Cliente
    }
}
