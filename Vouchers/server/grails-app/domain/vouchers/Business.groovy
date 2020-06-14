package vouchers

class Business extends User {

    String name
    String phone_number
    Address address
    String website
    String category
    //TODO: Create domain to keep all social networks
    static hasMany = [counterfoils: Counterfoil]

    static hasMany = [products: Product]

    static mapping = {
        products cascade: 'all-delete-orphan'
    }

    static constraints = {
        name blank: false, nullable: false
        phone_number blank: false, nullable: false
        address blank: false, nullable: false
        website nullable: true
        category blank: false, nullable: false
    }
}
