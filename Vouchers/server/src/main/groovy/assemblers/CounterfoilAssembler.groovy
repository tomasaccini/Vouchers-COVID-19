package assemblers

import commands.CounterfoilCommand
import templates.ConcreteObjectAssembler
import vouchers.Counterfoil

class CounterfoilAssembler extends ConcreteObjectAssembler<Counterfoil, CounterfoilCommand> {

    VoucherInformationAssembler voucherInformationAssembler
    VoucherAssembler voucherAssembler
    
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

        bean.voucherInformationCommand = voucherInformationAssembler.toBean(domain.voucherInformation)
        bean.vouchersCommand = voucherAssembler.toBeans(domain.vouchers.asList())

        return bean
    }

    @Override
    Counterfoil fromBean(CounterfoilCommand bean) {

        Counterfoil domain 	= super.fromBean(bean)

        domain.voucherInformation = voucherInformationAssembler.fromBean(bean.voucherInformationCommand)
        domain.vouchers = voucherAssembler.fromBeans(bean.vouchersCommand.asList())

        return domain
    }
}
