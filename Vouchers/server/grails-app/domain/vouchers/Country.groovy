package vouchers

class Country {

    String name

    static constraints = {
        name			blank: false, nullable: false, unique: true
    }

    @Override
    String toString() {
        return name
    }
}
