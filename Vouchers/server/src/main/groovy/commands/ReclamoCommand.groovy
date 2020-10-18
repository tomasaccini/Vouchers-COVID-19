package commands

import vouchers.MensajeReclamo

class ReclamoCommand {
    Long id
    Long clienteId
    Long negocioId
    Date fechaUltimoMensaje
    List<MensajeReclamoCommand> mensajes = []
}
