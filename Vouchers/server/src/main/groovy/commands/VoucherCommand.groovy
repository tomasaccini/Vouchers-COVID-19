package commands

import grails.validation.Validateable
import enums.states.VoucherEstado
import vouchers.Voucher

class VoucherCommand implements Validateable {

    Long id
    Long version
    InformacionVoucherCommand informacionVoucherCommand
    Date dateCreated
    VoucherEstado state
    Date lastStateChange = new Date()
    NegocioCommand negocioCommand
    Boolean reclamoAbierto
    String clienteEmail

    static constraints = {
        importFrom Voucher
    }
}
