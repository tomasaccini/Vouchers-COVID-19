package vouchers

import enums.InteraccionType

class InteraccionClienteTarifario {

    InteraccionType interaccion
    Date dateCreated = new Date()
    Cliente cliente
    Tarifario tarifario

    static constraints = {
        interaccion nullable: false
        dateCreated nullable: false
        cliente nullable: false
        tarifario nullable: false
    }
}
