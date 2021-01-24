package assemblers

import commands.TalonarioCommand
import templates.ConcreteObjectAssembler
import vouchers.Talonario

class TalonarioAssembler extends ConcreteObjectAssembler<Talonario, TalonarioCommand> {

    InformacionVoucherAssembler informacionVoucherAssembler
    VoucherAssembler voucherAssembler

    @Override
    protected Talonario getEntity(Long id) {
        return (id == null || id == 0) ? new Talonario() : Talonario.get(id)
    }

    @Override
    protected TalonarioCommand createBean() {
        return new TalonarioCommand()
    }

    @Override
    TalonarioCommand toBean(Talonario domain) {

        TalonarioCommand bean = super.toBean(domain)

        bean.negocioNombre = domain.negocio.nombre
        bean.informacionVoucherCommand = informacionVoucherAssembler.toBean(domain.informacionVoucher)
        bean.rating = domain.obtenerRating()

        if (domain.vouchers) {
            bean.cantidadVendida = domain.vouchers.asList().size()
        }

        return bean
    }

    @Override
    Talonario fromBean(TalonarioCommand bean) {

        Talonario domain = super.fromBean(bean)

        domain.informacionVoucher = informacionVoucherAssembler.fromBean(bean.informacionVoucherCommand)

        return domain
    }
}
