package vouchers

import enums.TrackingType

class Tracking {

    TrackingType type
    Client client
    VoucherInformation voucherInformation

    static constraints = {
        type nullable: false
        client nullable: false
        voucher nullable: false
    }
}
