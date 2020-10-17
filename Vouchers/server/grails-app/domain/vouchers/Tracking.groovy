package vouchers

import enums.TrackingType

class Tracking {

    TrackingType type
    Cliente cliente
    InformacionVoucher informacionVoucher

    static constraints = {
        type nullable: false
        cliente nullable: false
        informacionVoucher nullable: false
    }
}
