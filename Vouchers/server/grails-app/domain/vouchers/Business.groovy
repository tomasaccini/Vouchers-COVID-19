package vouchers

class Business {

    String name
    String email
    String phone_number
    Address address
    String website
    //TODO: Create domain to keep all social networks

    static hasMany = [products: Product]

    static mapping = {
        products cascade: 'all-delete-orphan'
    }

    static constraints = {
        name                    blank: false, nullable: false
        email                   email: true, blank: false, nullable: false
        phone_number            blank: false, nullable: false
        address                 blank: false, nullable: false
        website                 blank: false, nullable: true
    }
}
