package commands

import grails.validation.Validateable
import vouchers.Cliente

class ClienteCommand implements Validateable {

    Long id
    Long version
    String nombreCompleto
    String numeroTelefonico
    String email
    List<VoucherCommand> vouchersCommand

    static constraints = {
        importFrom Cliente
    }
}
