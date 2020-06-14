package vouchers

class Item {

    Product product
    Integer quantity

    static constraints = {
        product     nullable: false
        quantity    nullable: false, default: 1, min: 1
    }
}
