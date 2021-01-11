package vouchers

import enums.states.ReclamoEstado

class Reclamo {

    String descripcion
    Date fechaCreacion = new Date()
    Date fechaUltimoMensaje = new Date()
    Cliente cliente
    Negocio negocio
    ReclamoEstado estado = ReclamoEstado.Abierto
    Set<MensajeReclamo> mensajes = []

    static belongsTo = [voucher: Voucher]

    static hasMany = [mensajes: MensajeReclamo]
    static constraints = {
        descripcion nullable: false
        fechaCreacion nullable: false
        cliente nullable: false
        negocio nullable: false
        estado nullable: false
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

        estado = ReclamoEstado.Respondido

        _agregarMensaje(mensaje, duenio)
    }

    void cerrar(Usuario usuarioCerrador) {
        if (usuarioCerrador.id != cliente.id) {
            throw new RuntimeException("El usuario " + usuarioCerrador.id + " no puede cerrar este. Solo el cliente que creo el reclamo puede hacerlo")
        }

        if (estado == ReclamoEstado.Cerrado) {
            throw new RuntimeException("El reclamo ya fue cerrado")
        }

        estado = ReclamoEstado.Cerrado
        this.save(flush: true, failOnError: true)
    }

    void reabrir() {
        estado = ReclamoEstado.Abierto
    }

    Boolean estaCerrado() {
        return estado == ReclamoEstado.Cerrado
    }

    Boolean perteneceAUsuario(Usuario usuarioCerrador) {
        usuarioCerrador.id == cliente.id
    }

    private void _agregarMensaje(String mensaje, Usuario duenio) {
        if (estado == ReclamoEstado.Cerrado) {
            throw new RuntimeException("No se pueden agregar mas mensajes a un reclamo cerrado")
        }
        this.save(flush: true, failOnError: true)

        MensajeReclamo mensajeReclamo = new MensajeReclamo(duenio: duenio, texto: mensaje, fecha: new Date(), reclamo: this)
        mensajeReclamo.save(flush:true, failOnError: true)

        this.addToMensajes(mensajeReclamo)
        fechaUltimoMensaje = new Date()

        this.save(flush: true, failOnError: true)
    }
}
