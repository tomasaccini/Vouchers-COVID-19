package vouchers

class Business extends User {

    String name
    String phone_number
    Address address
    String website
    String category
    Set counterfoils = []
    Set products = []

    static hasMany = [counterfoils: Counterfoil, products: Product]

    static mapping = {
        products cascade: 'save-update'
        counterfoils cascade: 'save-update'
    }

    static constraints = {
        name blank: false, nullable: false
        phone_number blank: false, nullable: false
        address blank: false, nullable: false
        website nullable: true
        category blank: false, nullable: false
    }

    boolean isOwnerOfVoucher(Voucher v) {
        counterfoils.contains(v.getCounterfoil())
    }

    boolean activateCounterfoil(Counterfoil c) {
        if (!counterfoils.contains(c)) {
            throw new RuntimeException("The business is not the owner of the Counterfoil")
        }
        c.activate()
    }

    boolean deactivateCounterfoil(Counterfoil c) {
        if (!counterfoils.contains(c)) {
            throw new RuntimeException("The business is not the owner of the Counterfoil")
        }
        c.deactivate()
    }
}
