package vouchers

import enums.TrackingType

class Tracking {

    TrackingType type
    Client client

    static constraints = {
        // !!!! type constraint
        // client: false
    }
}
