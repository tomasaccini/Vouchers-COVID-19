package vouchers

class Client extends User {
    String full_name
    String phone_number
    static hasMany = [vouchers: Voucher]


    static constraints = {
        full_name nullable: false, blank: false
        phone_number nullable: true, blank: true
    }
}
