package vouchers

class Client extends User {

    String fullName
    String phoneNumber
    Set vouchers = []

    static hasMany = [vouchers: Voucher]

    static constraints = {
        fullName nullable: false, blank: false
        phoneNumber nullable: true, blank: true
    }

    Voucher buyVoucher(Counterfoil c) {
        Voucher v = c.createVoucher()
        addToVouchers(v)
        v
    }

    boolean retireVoucher(Voucher v){
        if (!vouchers.contains(v)) {
            throw new RuntimeException("The client is not the owner of the Voucher")
        }
        if (!v.isRetirable()) {
            throw new RuntimeException("Voucher has been already retired or is expired")
        }
        v.retire()
    }
}
