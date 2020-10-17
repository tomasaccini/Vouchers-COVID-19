package vouchers

class MensajeReclamo {

    Usuario duenio
    String texto
    Date dateCreated = new Date()

    static constraints = {
        duenio nullable: false
        texto nullable: false
        dateCreated nullable: false
    }
}
