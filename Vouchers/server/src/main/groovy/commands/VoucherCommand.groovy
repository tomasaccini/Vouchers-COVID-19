package commands

import grails.validation.Validateable
import enums.states.VoucherState
import vouchers.Voucher

class VoucherCommand implements Validateable {

    Long id
    Long version
    InformacionVoucherCommand informacionVoucherCommand
    Date dateCreated
    VoucherState state
    Date lastStateChange = new Date()
    NegocioCommand negocio

    static constraints = {
        importFrom Voucher
    }
}
