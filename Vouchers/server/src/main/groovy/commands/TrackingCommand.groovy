package commands

import enums.TrackingType
import grails.validation.Validateable
import vouchers.Tracking

class TrackingCommand implements Validateable {

    Long id
    ClientCommand clientCommand
    TrackingType type


    static constraints = {
        importFrom Tracking
    }
}
