package assemblers

import commands.CounterfoilCommand
import templates.ConcreteObjectAssembler
import vouchers.Tarifario

class CounterfoilAssembler extends ConcreteObjectAssembler<Tarifario, CounterfoilCommand> {

    VoucherInformationAssembler voucherInformationAssembler
    VoucherAssembler voucherAssembler
    
    @Override
    protected Tarifario getEntity(Long id) {
        return (id == null || id == 0) ? new Tarifario() : Tarifario.get(id)
    }

    @Override
    protected CounterfoilCommand createBean() {
        return new CounterfoilCommand()
    }

    @Override
    CounterfoilCommand toBean(Tarifario domain) {

        CounterfoilCommand bean = super.toBean(domain)

        bean.voucherInformationCommand = voucherInformationAssembler.toBean(domain.informacionVoucher)

        if (domain.vouchers){
            bean.vouchersCommand = voucherAssembler.toBeans(domain.vouchers.asList())
        }

        return bean
    }

    @Override
    Tarifario fromBean(CounterfoilCommand bean) {

        Tarifario domain 	= super.fromBean(bean)

        domain.informacionVoucher = voucherInformationAssembler.fromBean(bean.voucherInformationCommand)

        if (bean.vouchersCommand){
            domain.vouchers = voucherAssembler.fromBeans(bean.vouchersCommand.asList())
        }

        return domain
    }
}
