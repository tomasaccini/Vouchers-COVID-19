package vouchers

class InformacionVoucher {

    private final RATING_DEFAULT = 3

    double precio
    String descripcion // descripcion general del cupón
    Long rating
    Date validoDesde
    Date validoHasta
    Set<Item> items = []


    static hasMany = [items: Item]

    static mapping = {
        items cascade: 'save-update'
    }

    static constraints = {
        precio blank: false, nullable: false, default: 0
        descripcion blank: false, nullable: false
        validoDesde blank: false, nullable: false
        validoHasta blank: false, nullable: false
        rating nullable: true
    }

    // TODO Isn't there something like deep clone not to do this manually?
    InformacionVoucher duplicar() {
        Set<Item> nuevosItems = []

        for (def item : items) {
            Item nuevoItem = new Item(producto: item.producto, cantidad: item.cantidad)
            nuevosItems.add(nuevoItem)
        }

        new InformacionVoucher(precio: precio, descripcion: descripcion, validoDesde: validoDesde, validoHasta: validoHasta, items: nuevosItems)
    }

    Long obtenerRating() {
        if (rating == null) {
            return RATING_DEFAULT
        }

        return rating
    }
}
