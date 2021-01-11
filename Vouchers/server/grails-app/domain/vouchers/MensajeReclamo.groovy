package vouchers

class MensajeReclamo {

    Usuario duenio
    String texto
    Date fechaCreacion = new Date()
    Date fecha

    static constraints = {
        duenio nullable: false
        texto nullable: false
        fechaCreacion nullable: false
        fecha nullable: false
    }
}
