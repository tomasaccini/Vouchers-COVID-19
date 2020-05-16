package vouchers

class User {

    String fullname
    String email
    String phone_number
    String username
    String password
    String firebase_id
    Boolean verified_account

    static constraints = {
        email email: true, blank: false, nullable: false
        verified_account blank: false, nullable: false, default: false
    }
}
