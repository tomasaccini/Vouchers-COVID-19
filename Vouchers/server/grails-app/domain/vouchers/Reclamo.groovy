package vouchers

import enums.states.ReclamoState

class Reclamo {

    String descripcion
    Date dateCreated
    Date fechaUltimoMensaje = new Date()
    Cliente cliente
    Negocio negocio
    // TODO: corregir la logica de negocio de esto al ser chat. Tener en cuenta que el primer mensaje se agrega a mano y ya pasa a respondido !!!!
    ReclamoState state = ReclamoState.Abierto
    Set<MensajeReclamo> mensajes = []

    static belongsTo = [voucher: Voucher]

    static hasMany = [mensajes: MensajeReclamo]
    static constraints = {
        descripcion nullable: false
        dateCreated nullable: false
        cliente nullable: false
        negocio nullable: false
        state nullable: false
    }

    void agregarMensaje(String mensaje, Cliente duenio) {
        if (cliente.id != duenio.id) {
            throw new RuntimeException("El duenio del mensaje (${duenio.id}) no es el cliente relacionado con el reclamo (${cliente.id})")
        }

        _agregarMensaje(mensaje, duenio)
    }

    void agregarMensaje(String mensaje, Negocio duenio) {
        if (negocio.id != duenio.id) {
            throw new RuntimeException("El duenio del mensaje (${duenio.id}) no es el negocio relacionado con el reclamo (${negocio.id})")
        }

        _agregarMensaje(mensaje, duenio)
    }

    private void _agregarMensaje(String mensaje, Usuario owner) {
        if (state == ReclamoState.Cerrado) {
            throw new RuntimeException("No se pueden agregar mas mensajes a un reclamo cerrado")
        }

        MensajeReclamo mensajeReclamo = new MensajeReclamo(duenio: owner, texto: mensaje, fecha: new Date())
        mensajeReclamo.save(flush:true, failOnError: true)

        this.addToMensajes(mensajeReclamo)
        state = ReclamoState.Respondido
        fechaUltimoMensaje = new Date()

        this.save(flush:true, failOnError: true)
    }

    void cerrar() {
        state = ReclamoState.Cerrado
    }

    void reabrir() {
        state = mensajes.size() == 0 ? ReclamoState.Abierto : ReclamoState.Respondido
    }

    Boolean estaCerrado() {
        return state == ReclamoState.Cerrado
    }
}
