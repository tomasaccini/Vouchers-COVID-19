package vouchers

class Counterfoil {

    VoucherInformation voucherInformation
    int stock
    Set vouchers = []
    int amountSold
    boolean isActive
    //TODO: add state
    boolean active = false

    static belongsTo = [business: Business]

    static hasMany = [vouchers: Voucher]

    static mapping = {
        vouchers cascade: 'save-update'
    }
    static constraints = {
        voucherInformation      blank: false, nullable: false
        stock                   blank: false, nullable: false, default: 0
        active blank:false, nullable: false, default: false
    }

    boolean activate() {
        if (active) {
            return false
        }
        active = true
        true
    }

    boolean deactivate() {
        if (!active) {
            return false
        }
        active = false
        true
    }
}
