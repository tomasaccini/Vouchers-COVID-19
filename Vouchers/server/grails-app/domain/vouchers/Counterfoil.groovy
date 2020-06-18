package vouchers

class Counterfoil {

    VoucherInformation voucherInformation
    int stock
    Set vouchers = []
    int amountSold = 0
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

}
