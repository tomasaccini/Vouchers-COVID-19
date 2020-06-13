package vouchers

class VoucherInformation {

    double price
    String description
    Date valid_from
    Date valid_until
    static hasMany = [items: Item]

    static constraints = {
        price blank:false, nullable: false, default: 0
        description blank:false, nullable: false
        valid_from blank:false, nullable: false
        valid_until blank:false, nullable: false
    }
}
