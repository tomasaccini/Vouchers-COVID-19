package assemblers

import commands.VoucherInformationCommand
import templates.ConcreteObjectAssembler
import vouchers.VoucherInformation

class VoucherInformationAssembler  extends ConcreteObjectAssembler<VoucherInformation, VoucherInformationCommand> {

    ItemAssembler itemAssembler

    @Override
    protected VoucherInformation getEntity(Long id) {
        return (id == null || id == 0) ? new VoucherInformation() : VoucherInformation.get(id)
    }

    @Override
    protected VoucherInformationCommand createBean() {
        return new VoucherInformationCommand()
    }

    @Override
    VoucherInformationCommand toBean(VoucherInformation domain) {

        VoucherInformationCommand bean = super.toBean(domain)

        if (domain.items){
            bean.itemsCommand = itemAssembler.toBeans(domain.items.asList())
        }

        return bean
    }

    @Override
    VoucherInformation fromBean(VoucherInformationCommand bean) {

        VoucherInformation domain = super.fromBean(bean)

        if (bean.itemsCommand){
            domain.items = itemAssembler.fromBeans(bean.itemsCommand.asList())
        }

        return domain
    }

}
