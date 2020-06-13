package vouchers

class Product {

    String name
    String description
    //TODO: Add category

    static belongsTo = [business: Business]

    static constraints = {
        name blank: false, nullable: false
        description blank: false, nullable: true
    }
}