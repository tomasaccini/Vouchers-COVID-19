package assemblers

import commands.CounterfoilCommand
import templates.ConcreteObjectAssembler
import vouchers.Counterfoil

class CounterfoilAssembler extends ConcreteObjectAssembler<Counterfoil, CounterfoilCommand> {

    VoucherInformationAssembler voucherInformationAssembler

    @Override
    protected Counterfoil getEntity(Long id) {
        return (id == null || id == 0) ? new Counterfoil() : Counterfoil.get(id)
    }

    @Override
    protected CounterfoilCommand createBean() {
        return new CounterfoilCommand()
    }

    @Override
    CounterfoilCommand toBean(Counterfoil domain) {

        CounterfoilCommand bean = super.toBean(domain)

        if(domain.voucherInformation) {
            bean.voucherInformationCommand = voucherInformationAssembler.toBean(domain.voucherInformation)
        }

        return bean
    }

    @Override
    Counterfoil fromBean(CounterfoilCommand bean) {

        Counterfoil domain 	= super.fromBean(bean)
        domain.voucherInformation = voucherInformationAssembler.fromBean(bean.voucherInformationCommand)

        return domain
    }
}
