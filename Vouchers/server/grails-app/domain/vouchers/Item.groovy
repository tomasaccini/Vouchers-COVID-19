package vouchers

class Item {

    Producto producto
    Integer cantidad

    static constraints = {
        producto nullable: false
        cantidad nullable: false, default: 1, min: 1
    }
}
