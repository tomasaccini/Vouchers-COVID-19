package commands

class TrackingCommand {

    Long id
    String type
    String clientID


    static constraints = {
        trackingType nullable: false, blank: false
        clientID nullable: false, blank: false
    }
}
