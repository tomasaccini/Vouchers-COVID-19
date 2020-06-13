package vouchers

class User {

    String email
    String username
    String password
    String firebase_id
    Boolean verified_account

    static constraints = {
        email               email: true, blank: false, nullable: false
        verified_account    blank: false, nullable: false, default: false
        username            nullable: false, blank: false
        password            nullable: false, password:true
    }


}
