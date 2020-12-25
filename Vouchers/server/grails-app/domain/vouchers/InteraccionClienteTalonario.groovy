package vouchers

import enums.InteraccionType

class InteraccionClienteTalonario {

    InteraccionType interaccion
    Date dateCreated = new Date()
    Cliente cliente
    Talonario talonario

    static constraints = {
        interaccion nullable: false
        dateCreated nullable: false
        cliente nullable: false
        talonario nullable: false
    }
}
