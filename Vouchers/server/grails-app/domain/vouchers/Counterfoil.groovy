package vouchers

class Counterfoil {

    VoucherInformation voucherInformation
    int stock
    int amountSold
    boolean isActive
    //TODO: add state
    boolean active = false

    static belongsTo = [business: Business]

    static hasMany = [vouchers: Voucher]

    static constraints = {
        voucherInformation blank: false, nullable: false
        stock blank: false, nullable: false, default: 0
        active blank:false, nullable: false, default: false
    }

    Voucher createVoucher() {
        if (!active) {
            throw new RuntimeException("Counterfoil is not active")
        }
        if (stock <= 0) {
            throw new RuntimeException("Voucher does not have stock")
        }
        Voucher v = new Voucher(voucherInformation: voucherInformation.duplicate(), dateCreated: new Date())
        v.setCounterfoil(this)
        addToVouchers(v)
        stock -= 1
        amountSold += 1
        v
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
