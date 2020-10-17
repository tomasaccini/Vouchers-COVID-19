package assemblers

import commands.VoucherInformationCommand
import templates.ConcreteObjectAssembler
import vouchers.InformacionVoucher

class VoucherInformationAssembler  extends ConcreteObjectAssembler<InformacionVoucher, VoucherInformationCommand> {

    ItemAssembler itemAssembler

    @Override
    protected InformacionVoucher getEntity(Long id) {
        return (id == null || id == 0) ? new InformacionVoucher() : InformacionVoucher.get(id)
    }

    @Override
    protected VoucherInformationCommand createBean() {
        return new VoucherInformationCommand()
    }

    @Override
    VoucherInformationCommand toBean(InformacionVoucher domain) {

        VoucherInformationCommand bean = super.toBean(domain)

        if (domain.items){
            bean.itemsCommand = itemAssembler.toBeans(domain.items.asList())
        }

        return bean
    }

    @Override
    InformacionVoucher fromBean(VoucherInformationCommand bean) {

        InformacionVoucher domain = super.fromBean(bean)

        if (bean.itemsCommand){
            domain.items = itemAssembler.fromBeans(bean.itemsCommand.asList())
        }

        return domain
    }

}
