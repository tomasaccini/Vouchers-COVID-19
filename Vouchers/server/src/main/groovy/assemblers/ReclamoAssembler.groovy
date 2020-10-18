package assemblers

import commands.MensajeReclamoCommand
import commands.ReclamoCommand
import templates.ConcreteObjectAssembler
import vouchers.MensajeReclamo
import vouchers.Reclamo

class ReclamoAssembler extends ConcreteObjectAssembler<Reclamo, ReclamoCommand> {

    @Override
    protected getEntity(Long id) {
        return (id == null || id == 0) ? new Reclamo() : Reclamo.get(id)
    }

    @Override
    protected createBean() {
        return new ReclamoCommand()
    }

    @Override
    ReclamoCommand toBean(Reclamo reclamo) {
        ReclamoCommand reclamoCommand = new ReclamoCommand(
                id: reclamo.id,
                clienteId: reclamo.cliente.id,
                negocioId: reclamo.negocio.id,
        )

        List<MensajeReclamo> mensajes = reclamo.mensajes.toList()
        mensajes = mensajes.sort { it.fecha }

        for (def mensaje : mensajes) {
            MensajeReclamoCommand mensajeCommand = new MensajeReclamoCommand(
                    duenioId: mensaje.duenio.id,
                    duenioEmail: mensaje.duenio.email,
                    texto: mensaje.texto,
                    fecha: mensaje.fecha,
            )
            reclamoCommand.mensajes.add(mensajeCommand)
        }

        return reclamoCommand
    }

    @Override
    Reclamo fromBean(ReclamoCommand reclamoCommand) {
        return super.fromBean(reclamoCommand)
    }
}
