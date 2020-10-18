package vouchers

class MensajeReclamo {

    Usuario duenio
    String texto
    Date dateCreated = new Date()
    Date fecha

    static constraints = {
        duenio nullable: false
        texto nullable: false
        dateCreated nullable: false
        fecha nullable: false
    }
}
