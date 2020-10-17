package vouchers

class Tarifario {

    InformacionVoucher informacionVoucher
    int stock
    Set vouchers = []
    int cantidadVendida = 0
    boolean activo = false

    static belongsTo = [negocio: Negocio]

    static hasMany = [vouchers: Voucher]

    static mapping = {
        vouchers cascade: 'save-update'
    }
    static constraints = {
        informacionVoucher      blank: false, nullable: false
        stock                   blank: false, nullable: false, default: 0
        activo blank:false, nullable: false, default: false
    }

}
