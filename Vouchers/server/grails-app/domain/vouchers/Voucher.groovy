package vouchers

import enums.states.VoucherState

class Voucher {

    VoucherInformation voucherInformation
    Date dateCreated = new Date()
    VoucherState state = VoucherState.BOUGHT
    Date lastStateChange = new Date()
    Complaint complaint
    Client client
    Counterfoil counterfoil

    static constraints = {
        voucherInformation     nullable: false, blank: true
        dateCreated            nullable: false
        state                  nullable: false, blank: false, default: VoucherState.BOUGHT
        lastStateChange        nullable: true
        complaint              nullable: true
        client                 nullable:true
        counterfoil            nullable:true
    }

    boolean isRetirable() {
        if (new Date() >= voucherInformation.validUntil) {
            state = VoucherState.EXPIRED
            lastStateChange = new Date()
        }
        return state == VoucherState.BOUGHT
    }

    boolean isConfirmable() {
        return state == VoucherState.PENDING_CONFIRMATION
    }

}
