package vouchers

import enums.states.VoucherState

class Voucher {

    InformacionVoucher informacionVoucher
    Date dateCreated = new Date()
    VoucherState state = VoucherState.Comprado
    Date lastStateChange = new Date()
    Reclamo reclamo
    Cliente cliente
    Tarifario tarifario

    static constraints = {
        informacionVoucher     nullable: false, blank: true
        dateCreated            nullable: false
        state                  nullable: false, blank: false, default: VoucherState.Comprado
        lastStateChange        nullable: true
        reclamo              nullable: true
        cliente                 nullable:true
        tarifario            nullable:true
    }

    boolean isRetirable() {
        if (new Date() >= informacionVoucher.validoHasta) {
            state = VoucherState.Expirado
            lastStateChange = new Date()
        }
        return state == VoucherState.Comprado
    }

    boolean isConfirmable() {
        return state == VoucherState.ConfirmacionPendiente
    }
}
