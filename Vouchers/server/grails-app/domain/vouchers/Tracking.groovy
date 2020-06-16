package vouchers

import enums.TrackingType

class Tracking {

    TrackingType type
    Client client

    static constraints = {
        type    nullable: false, blank: false
        client  nullable: false, blank: false
    }
}
