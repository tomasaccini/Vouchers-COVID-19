package vouchers

class Business {

    String name
    String email
    String phone_number
    Address address
    String website
    //TODO: Create domain to keep all social networks

    static constraints = {
        name                    blank: false, nullable: false
        email                   email: true, blank: false, nullable: false
        phone_number            blank: false, nullable: false
        address                 blank: false, nullable: false
        website                 blank: false, nullable: true
    }
}
