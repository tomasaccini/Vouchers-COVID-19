package vouchers

abstract class User {

    String email
    String password
    Boolean verifiedAccount

    static constraints = {
        email email: true, blank: false, nullable: false
        password password: true, blank: false, nullable: false
        verifiedAccount blank: false, nullable: false, default: false
    }

    void verify_account() {
        verifiedAccount = true
    }
}
