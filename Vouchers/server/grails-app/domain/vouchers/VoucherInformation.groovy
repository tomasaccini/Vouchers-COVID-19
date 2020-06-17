package vouchers

class VoucherInformation {

    double price
    // TODO remove description because it should be in product
    String description
    Date validFrom
    Date validUntil

    static hasMany = [items: Item]

    static mapping = {
        items cascade: 'save-update'
    }

    static constraints = {
        price blank:false, nullable: false, default: 0
        description blank:false, nullable: false
        validFrom blank:false, nullable: false
        validUntil blank:false, nullable: false
    }

    // TODO Isn't there something like deep clone not to do this manually?
    VoucherInformation duplicate() {
        new VoucherInformation(price: price, description: description, validFrom: validFrom, validUntil: validUntil, items: items.clone())
    }
}
