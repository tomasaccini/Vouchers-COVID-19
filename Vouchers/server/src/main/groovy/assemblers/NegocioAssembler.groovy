package assemblers


import commands.NegocioCommand
import templates.ConcreteObjectAssembler
import vouchers.Negocio

class NegocioAssembler extends ConcreteObjectAssembler<Negocio, NegocioCommand> {

    TalonarioAssembler talonarioAssembler

    @Override
    protected getEntity(Long id) {
        return (id == null || id == 0) ? new Negocio() : Negocio.get(id)
    }

    @Override
    protected createBean() {
        return new NegocioCommand()
    }

    @Override
    NegocioCommand toBean(Negocio negocio) {
        NegocioCommand command = super.toBean(negocio)
        command.talonariosCommands = []

        for (def talonario : negocio.talonarios) {
            command.talonariosCommands.add(talonarioAssembler.toBean(talonario))
        }

        return command
    }

    @Override
    Negocio fromBean(NegocioCommand negocioCommand) {
        return super.fromBean(negocioCommand)
    }
}
