package vouchers

import enums.InteractionType

class ClientCounterfoilInteraction {

    InteractionType interaction
    Date dateCreated = new Date()
    Client client
    Counterfoil counterfoil

    static constraints = {
        interaction nullable: false
        dateCreated nullable: false
        client nullable: false
        counterfoil nullable: false
    }
}
