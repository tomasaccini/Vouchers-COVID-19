package assemblers

import commands.TalonarioCommand
import templates.ConcreteObjectAssembler
import vouchers.Talonario

class CounterfoilAssembler extends ConcreteObjectAssembler<Talonario, TalonarioCommand> {

    VoucherInformationAssembler voucherInformationAssembler
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

        bean.voucherInformationCommand = voucherInformationAssembler.toBean(domain.informacionVoucher)

        if (domain.vouchers){
            bean.cantidadVendida = domain.vouchers.asList().size()
        }

        return bean
    }

    @Override
    Talonario fromBean(TalonarioCommand bean) {

        Talonario domain 	= super.fromBean(bean)

        domain.informacionVoucher = voucherInformationAssembler.fromBean(bean.voucherInformationCommand)

        return domain
    }
}
