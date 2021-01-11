package vouchers

class MensajeReclamo {

    Usuario duenio
    String texto
    Date fecha
    Reclamo reclamo

    static constraints = {
        duenio nullable: false
        texto nullable: false
        fecha nullable: false
        reclamo nullable: false
    }

    static belongsTo = [reclamo: Reclamo]
}
