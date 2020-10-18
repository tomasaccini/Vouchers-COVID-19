package commands

import vouchers.MensajeReclamo

class ReclamoCommand {
    Long id
    Long clienteId
    Long negocioId
    List<MensajeReclamoCommand> mensajes = []
}
