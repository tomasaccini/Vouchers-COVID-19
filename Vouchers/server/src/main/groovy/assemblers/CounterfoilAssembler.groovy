package assemblers

import commands.TarifarioCommand
import templates.ConcreteObjectAssembler
import vouchers.Tarifario

class CounterfoilAssembler extends ConcreteObjectAssembler<Tarifario, TarifarioCommand> {

    VoucherInformationAssembler voucherInformationAssembler
    VoucherAssembler voucherAssembler
    
    @Override
    protected Tarifario getEntity(Long id) {
        return (id == null || id == 0) ? new Tarifario() : Tarifario.get(id)
    }

    @Override
    protected TarifarioCommand createBean() {
        return new TarifarioCommand()
    }

    @Override
    TarifarioCommand toBean(Tarifario domain) {

        TarifarioCommand bean = super.toBean(domain)

        bean.voucherInformationCommand = voucherInformationAssembler.toBean(domain.informacionVoucher)

        if (domain.vouchers){
            bean.vouchersCommand = voucherAssembler.toBeans(domain.vouchers.asList())
        }

        return bean
    }

    @Override
    Tarifario fromBean(TarifarioCommand bean) {

        Tarifario domain 	= super.fromBean(bean)

        domain.informacionVoucher = voucherInformationAssembler.fromBean(bean.voucherInformationCommand)

        if (bean.vouchersCommand){
            domain.vouchers = voucherAssembler.fromBeans(bean.vouchersCommand.asList())
        }

        return domain
    }
}
