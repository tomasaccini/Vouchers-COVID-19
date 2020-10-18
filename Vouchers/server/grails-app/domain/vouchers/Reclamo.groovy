package vouchers

import enums.states.ReclamoState

class Reclamo {

    String descripcion
    Date dateCreated = new Date()
    Cliente cliente
    Negocio negocio
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
        if (cliente != duenio) {
            throw new RuntimeException("El duenio del mensaje no es el cliente relacionado con el reclamo")
        }

        _agregarMensaje(mensaje, duenio)
    }

    void agregarMensaje(String mensaje, Negocio duenio) {
        if (negocio != duenio) {
            throw new RuntimeException("El duenio del mensaje no es el negocio relacionado con el reclamo")
        }

        _agregarMensaje(mensaje, duenio)
    }

    private void _agregarMensaje(String mensaje, Usuario owner) {
        if (state == ReclamoState.Cerrado) {
            throw new RuntimeException("No se pueden agregar mas mensajes a un reclamo cerrado")
        }

        this.addToMensajes(new MensajeReclamo(duenio: owner, texto: mensaje))
        state = ReclamoState.Respondido
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
