package vouchers

class VoucherInformation {

    double price
    String description
    Date validFrom
    Date validUntil
    static hasMany = [items: Item]

    static constraints = {
        price blank:false, nullable: false, default: 0
        description blank:false, nullable: false
        validFrom blank:false, nullable: false
        validUntil blank:false, nullable: false
    }

    VoucherInformation duplicate() {
        new VoucherInformation(price: price, description: description, validFrom: validFrom, validUntil: validUntil, items: items.clone())
    }
}
