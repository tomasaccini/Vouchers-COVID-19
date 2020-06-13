package vouchers

class Business extends User {

    String name
    String phone_number
    String address
    String category
    String website
    String social_network

    static constraints = {
        name blank: false, nullable: false
        phone_number blank: false, nullable: false
        address blank: false, nullable: false
        category blank: false, nullable: false
    }
}
