package vouchers

class Client {

    User user
    String name
    String surname
    String phone_number

    static hasMany = [vouchers: Voucher]

    static constraints = {
        user                nullable: false
        name                nullable: false, blank: false
        surname             nullable: true, blank: false
        phone_number        nullable: true, blank: false
    }

}
