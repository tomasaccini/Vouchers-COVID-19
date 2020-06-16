package vouchers

import enums.states.VoucherState

class Voucher {

    VoucherInformation voucherInformation
    Date dateCreated = new Date()
    VoucherState state = VoucherState.BOUGHT
    Date lastStateChange = new Date()
    Complaint complaint

    static hasMany = [items: Item]

    static belongsTo = [client: Client, counterfoil: Counterfoil]

    static constraints = {
        voucherInformation     nullable: false, blank: true
        dateCreated            nullable: false
        state                  nullable: false, blank: false, default: VoucherState.BOUGHT
        lastStateChange        nullable: true
        complaint              nullable: true
    }

    boolean isRetirable() {
        if (new Date() >= voucherInformation.valid_until) {
            state = VoucherState.EXPIRED
            lastStateChange = new Date()
        }
        return state == VoucherState.BOUGHT
    }

    boolean retire() {
        if (!isRetirable()) {
            return false
        }
        state = VoucherState.PENDING_CONFIRMATION
        lastStateChange = new Date()
        true
    }

    boolean isConfirmable() {
        return state == VoucherState.PENDING_CONFIRMATION
    }

    boolean confirm() {
        if (!isConfirmable()) {
            return false
        }
        state = VoucherState.RETIRED
        lastStateChange = new Date()
        true
    }
}
