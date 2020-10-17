package vouchers

class InformacionVoucher {

    double precio
    // TODO remove description because it should be in product
    String descripcion
    Date validoDesde
    Date validoHasta

    static hasMany = [items: Item]

    static mapping = {
        items cascade: 'save-update'
    }

    static constraints = {
        precio blank:false, nullable: false, default: 0
        descripcion blank:false, nullable: false
        validoDesde blank:false, nullable: false
        validoHasta blank:false, nullable: false
    }

    // TODO Isn't there something like deep clone not to do this manually?
    InformacionVoucher duplicar() {
        new InformacionVoucher(precio: precio, descripcion: descripcion, validoDesde: validoDesde, validoHasta: validoHasta, items: items.clone())
    }
}
