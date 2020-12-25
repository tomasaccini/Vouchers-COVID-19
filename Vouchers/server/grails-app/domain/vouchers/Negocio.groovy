package vouchers

class Negocio extends Usuario {

    String nombre
    String numeroTelefonico
    Direccion direccion
    String website
    String categoria

    static hasMany = [talonarios: Talonario, products: Producto]

    static mapping = {
        products cascade: 'save-update'
        talonarios cascade: 'save-update'
    }

    static constraints = {
        nombre blank: false, nullable: false
        numeroTelefonico blank: false, nullable: false
        direccion blank: false, nullable: false
        website nullable: true
        categoria blank: false, nullable: false
    }

    boolean isOwnerOfVoucher(Voucher v) {
        this.talonarios.contains(v.getTalonario())
    }

    boolean isOwnerOfCounterfoil(Long id) {
        this.talonarios.any{ c -> c.id == id}
    }
}
