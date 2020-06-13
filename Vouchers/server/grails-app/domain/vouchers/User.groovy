package vouchers

abstract class User {

    String email
    String password
    Boolean verified_account

    static constraints = {
        email email: true, blank: false, nullable: false
        password password: true, blank: false, nullable: false
        verified_account blank: false, nullable: false, default: false
    }

    void verify_email() {
        verified_account = true
    }
}
