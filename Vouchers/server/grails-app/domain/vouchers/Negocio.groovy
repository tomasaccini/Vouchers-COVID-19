package vouchers

class Negocio extends Usuario {

    String nombre
    String numeroTelefonico
    Direccion direccion
    String website
    String categoria

    static hasMany = [tarifarios: Tarifario, products: Producto]

    static mapping = {
        products cascade: 'save-update'
        tarifarios cascade: 'save-update'
    }

    static constraints = {
        nombre blank: false, nullable: false
        numeroTelefonico blank: false, nullable: false
        direccion blank: false, nullable: false
        website nullable: true
        categoria blank: false, nullable: false
    }

    boolean isOwnerOfVoucher(Voucher v) {
        this.tarifarios.contains(v.getTarifario())
    }

    boolean isOwnerOfCounterfoil(Long id) {
        this.tarifarios.any{ c -> c.id == id}
    }

}
