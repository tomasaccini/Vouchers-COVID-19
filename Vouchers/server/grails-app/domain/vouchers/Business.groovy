package vouchers

class Business {

    String name
    String email
    String phone_number
    String address
    String category
    String website
    String social_network

    static constraints = {
        name blank: false, nullable: false
        email email: true, blank: false, nullable: false
        phone_number blank: false, nullable: false
        address blank: false, nullable: false
    }
}
