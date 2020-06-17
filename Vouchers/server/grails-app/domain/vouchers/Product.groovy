package vouchers

import enums.ProductType

class Product {

    String name
    String description
    ProductType type

    static belongsTo = [business: Business]

    static constraints = {
        name                blank: false, nullable: false
        description         blank: false, nullable: true
        type                nullable: false
    }
}