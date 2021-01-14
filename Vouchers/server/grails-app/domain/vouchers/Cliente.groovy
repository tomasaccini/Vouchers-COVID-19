package vouchers

class Cliente extends Usuario {

    String nombreCompleto
    String numeroTelefonico
    Set vouchers = []

    static hasMany = [vouchers: Voucher]

    static constraints = {
        nombreCompleto nullable: false, blank: false
        numeroTelefonico nullable: true, blank: true
    }

    Voucher getVoucher(Long voucherId) {
        return vouchers.find { v -> v.id == voucherId }
    }

}
