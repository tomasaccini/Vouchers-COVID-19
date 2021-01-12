package vouchers

class Negocio extends Usuario {

    String nombre
    String numeroTelefonico
    Direccion direccion
    String website
    String categoria

    static hasMany = [talonarios: Talonario, productos: Producto]

    static mapping = {
        productos cascade: 'save-update'
        talonarios cascade: 'save-update'
    }

    static constraints = {
        nombre blank: false, nullable: false
        numeroTelefonico blank: false, nullable: false
        direccion blank: false, nullable: false
        website nullable: true
        categoria blank: false, nullable: false
    }

    boolean esDuenioDeVoucher(Voucher v) {
        this.talonarios.contains(v.getTalonario())
    }

    boolean esDuenioDeTalonario(Long id) {
        this.talonarios.any{ c -> c.id == id}
    }

    boolean tieneTalonarioConDescripcion(String descripcion){
        for (talonario in this.talonarios){
            if (talonario.informacionVoucher.descripcion == descripcion)
                return true
        }
        return false
    }

    List<Voucher> obtenerVouchersConfirmables() {
        List<Voucher> vouchers = talonarios.collect { it.vouchers }.flatten() as List<Voucher>
        println(vouchers)
        return vouchers.findAll { it.esConfirmable() }
    }
}
