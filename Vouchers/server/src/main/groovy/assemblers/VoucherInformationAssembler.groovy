package assemblers

import commands.InformacionVoucherCommand
import templates.ConcreteObjectAssembler
import vouchers.InformacionVoucher

class VoucherInformationAssembler extends ConcreteObjectAssembler<InformacionVoucher, InformacionVoucherCommand> {

    ItemAssembler itemAssembler

    @Override
    protected InformacionVoucher getEntity(Long id) {
        return (id == null || id == 0) ? new InformacionVoucher() : InformacionVoucher.get(id)
    }

    @Override
    protected InformacionVoucherCommand createBean() {
        return new InformacionVoucherCommand()
    }

    @Override
    InformacionVoucherCommand toBean(InformacionVoucher domain) {

        InformacionVoucherCommand bean = super.toBean(domain)

        if (domain.items) {
            bean.itemsCommand = itemAssembler.toBeans(domain.items.asList())
        }

        return bean
    }

    @Override
    InformacionVoucher fromBean(InformacionVoucherCommand bean) {

        InformacionVoucher domain = super.fromBean(bean)

        if (bean.itemsCommand) {
            domain.items = itemAssembler.fromBeans(bean.itemsCommand.asList())
        }

        return domain
    }

}
