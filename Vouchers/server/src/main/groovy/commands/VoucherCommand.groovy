package commands

import enums.states.VoucherEstado
import grails.validation.Validateable
import vouchers.Voucher

class VoucherCommand implements Validateable {

    Long id
    Long version
    InformacionVoucherCommand informacionVoucherCommand
    Date fechaCreacion
    VoucherEstado estado
    Date ultimoCambioEstado = new Date()
    NegocioCommand negocioCommand
    Boolean reclamoAbierto
    Long reclamoId
    ReclamoCommand reclamoCommand
    String clienteEmail

    static constraints = {
        importFrom Voucher
    }
}
