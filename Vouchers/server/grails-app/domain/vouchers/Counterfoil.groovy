package vouchers

class Counterfoil {

    VoucherInformation voucherInformation
    int stock
    Set vouchers = []
    //TODO: add state

    static belongsTo = [business: Business]

    static hasMany = [vouchers: Voucher]

    static mapping = {
        vouchers cascade: 'save-update'
    }
    static constraints = {
        voucherInformation      blank: false, nullable: false
        stock                   blank: false, nullable: false, default: 0
    }

}
