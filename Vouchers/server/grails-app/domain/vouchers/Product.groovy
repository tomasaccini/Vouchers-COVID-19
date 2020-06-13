package vouchers

class Product {

    String name
    String description
    //TODO: Add category

    static belongsTo = [business: Business]

    static constraints = {
        name            nullable: false, blank: false
        description     nullable: true, blank: false

    }

}

