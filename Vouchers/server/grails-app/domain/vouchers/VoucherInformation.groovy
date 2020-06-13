package vouchers

class VoucherInformation {

    ArrayList<Product> products
    double price
    String description
    Date valid_from
    Date valid_until

    static constraints = {
        products blank: false, nullable: false, default: new ArrayList<Product>()
        price blank:false, nullable: false, default: 0
        description blank:false, nullable: false
        valid_from blank:false, nullable: false
        valid_until blank:false, nullable: false
    }
}
