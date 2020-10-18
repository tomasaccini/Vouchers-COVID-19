package vouchers

import enums.states.VoucherState
import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException

class Voucher {

    InformacionVoucher informacionVoucher
    Date dateCreated = new Date()
    VoucherState state = VoucherState.Comprado
    Date lastStateChange = new Date()
    // !!!! Optional
    Reclamo reclamo
    Cliente cliente
    Tarifario tarifario

    static constraints = {
        informacionVoucher     nullable: false, blank: false
        dateCreated            nullable: false
        state                  nullable: false, blank: false, default: VoucherState.Comprado
        lastStateChange        nullable: true
        reclamo                nullable: true
        cliente                nullable: false, blank: false
        tarifario              nullable: false, blank: false
    }

    boolean esRetirable() {
        if (new Date() >= informacionVoucher.validoHasta) {
            state = VoucherState.Expirado
            lastStateChange = new Date()
        }
        return state == VoucherState.Comprado
    }

    boolean esConfirmable() {
        return state == VoucherState.ConfirmacionPendiente
    }
}
