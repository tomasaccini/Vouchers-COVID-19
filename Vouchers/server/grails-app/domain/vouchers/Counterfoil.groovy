package vouchers

class Counterfoil {

    VoucherInformation voucherInformation
    int stock
    HashSet<Voucher> vouchers_sold

    static constraints = {
        voucherInformation blank: false, nullable: false
        stock blank: false, nullable: false, default: 0
        vouchers_sold blank: false, nullable: false, default: new HashSet<Voucher>()
    }
}
