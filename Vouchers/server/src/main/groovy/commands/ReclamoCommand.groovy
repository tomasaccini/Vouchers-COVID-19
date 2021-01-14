package commands

class ReclamoCommand {
    Long id
    Long clienteId
    String clienteEmail
    Long negocioId
    String negocioNombre
    String voucherDescripcion
    String negocioEmail
    Date fechaUltimoMensaje
    String state
    List<MensajeReclamoCommand> mensajes = []
}
