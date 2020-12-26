package commands

import vouchers.MensajeReclamo

class ReclamoCommand {
    Long id
    Long clienteId
    String clienteEmail
    Long negocioId
    String negocioEmail
    Date fechaUltimoMensaje
    String state
    List<MensajeReclamoCommand> mensajes = []
}
