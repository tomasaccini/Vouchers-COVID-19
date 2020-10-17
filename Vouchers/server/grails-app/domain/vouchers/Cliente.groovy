package vouchers

class Cliente extends Usuario {

    String fullName
    String phoneNumber
    Set vouchers = []

    static hasMany = [vouchers: Voucher]

    static constraints = {
        fullName nullable: false, blank: false
        phoneNumber nullable: true, blank: true
    }

}
