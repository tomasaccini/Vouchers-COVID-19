package vouchers

class Pais {

    String name

    static constraints = {
        name			blank: false, nullable: false, unique: true
    }

    @Override
    String toString() {
        return name
    }
}
