package commands

import grails.validation.Validateable
import vouchers.Cliente

class ClienteCommand implements Validateable {

    Long id
    String nombreCompleto
    String numeroTelefonico
    List<VoucherCommand> vouchersCommand

    static constraints = {
        importFrom Cliente
    }
}
