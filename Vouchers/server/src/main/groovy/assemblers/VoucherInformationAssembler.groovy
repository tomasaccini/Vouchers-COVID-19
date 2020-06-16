package assemblers

import commands.VoucherInformationCommand
import templates.ConcreteObjectAssembler
import vouchers.VoucherInformation

class VoucherInformationAssembler  extends ConcreteObjectAssembler<VoucherInformation, VoucherInformationCommand> {

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

        return bean
    }

    @Override
    VoucherInformation fromBean(VoucherInformationCommand bean) {

        VoucherInformation domain = super.fromBean(bean)

        return domain
    }

}
