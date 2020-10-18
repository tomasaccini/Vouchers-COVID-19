package assemblers


import commands.NegocioCommand
import templates.ConcreteObjectAssembler
import vouchers.Negocio

class NegocioAssembler extends ConcreteObjectAssembler<Negocio, NegocioCommand> {

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
        return super.toBean(negocio)
    }

    @Override
    Negocio fromBean(NegocioCommand negocioCommand) {
        return super.fromBean(negocioCommand)
    }
}
