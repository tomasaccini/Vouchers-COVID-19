package vouchers

import enums.TipoInteraccion

class InteraccionClienteTalonario {

    TipoInteraccion interaccion
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
