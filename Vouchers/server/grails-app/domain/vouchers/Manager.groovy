package vouchers

class Manager {

    User user

    static hasMany = [companies: Business]

    static constraints = {
        user        nullable: false
        companies   nullable: true
    }
}
