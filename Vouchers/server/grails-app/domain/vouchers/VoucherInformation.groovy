package vouchers

class VoucherInformation {

    double price
    // TODO remove description because it should be in product
    String description
    Product product
    Date valid_from
    Date valid_until
    static hasMany = [items: Item]

    static constraints = {
        price blank:false, nullable: false, default: 0
        description blank:false, nullable: false
        product nullable: false
        valid_from blank:false, nullable: false
        valid_until blank:false, nullable: false
    }

    // TODO Isn't there something like deep clone not to do this manually?
    VoucherInformation duplicate() {
        new VoucherInformation(price: price, description: description, product: product, valid_from: valid_from, valid_until: valid_until, items: items.clone())
    }
}
