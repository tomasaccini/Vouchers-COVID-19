package vouchers

class Business extends User {

    String name
    String phone_number
    Address address
    String website
    String category
    static hasMany = [counterfoils: Counterfoil, products: Product]

    static mapping = {
        products cascade: 'all-delete-orphan'
    }

    static constraints = {
        name blank: false, nullable: false
        phone_number blank: false, nullable: false
        address blank: false, nullable: false
        website nullable: true
        category blank: false, nullable: false
    }

    void addProduct(Product p) {
        addToProducts(p)
        p.setBusiness(this)
    }

    void addCounterfoil(Counterfoil c) {
        addToCounterfoils(c)
        c.setBusiness(this)
    }

    boolean isOwnerOfVoucher(Voucher v) {
        counterfoils.contains(v.getCounterfoil())
    }

    boolean confirmRetireVoucher(Voucher v){
        if (!isOwnerOfVoucher(v)) {
            throw new RuntimeException("The business is not the owner of the Voucher")
        }
        if (!v.isConfirmable()) {
            throw new RuntimeException("Voucher is not confirmable")
        }
        v.confirm()
    }
}
