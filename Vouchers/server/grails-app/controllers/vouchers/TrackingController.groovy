package vouchers

import grails.rest.RestfulController

class TrackingController extends RestfulController {

    static responseFormats = ['json', 'xml']

    TrackingController() {
        super(Tracking)
    }
}
