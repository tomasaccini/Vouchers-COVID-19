package vouchers

class Address {

    String street
    String number
    String apartment
    String province
    Country country


    static constraints = {
        street      nullable: false, blank: false
        number      nullable: false, blank: false
        apartment   nullable: true, blank: false
        province    nullable: true, blank: true
        country     nullable: false
    }

    @Override
    String toString() {
        return "${street}-${number}-${country}"
    }
}
