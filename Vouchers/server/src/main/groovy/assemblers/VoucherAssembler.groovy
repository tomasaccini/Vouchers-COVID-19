package assemblers

import commands.VoucherCommand
import templates.ConcreteObjectAssembler
import vouchers.Voucher

class VoucherAssembler extends ConcreteObjectAssembler<Voucher, VoucherCommand>{

    VoucherInformationAssembler voucherInformationAssembler

    @Override
    protected Voucher getEntity(Long id) {
        return (id == null || id == 0) ? new Voucher() : Voucher.get(id)
    }

    @Override
    protected VoucherCommand createBean() {
        return new VoucherCommand()
    }

    @Override
    VoucherCommand toBean(Voucher domain) {
        VoucherCommand bean = super.toBean(domain)

        bean.voucherInformationCommand = voucherInformationAssembler(domain.voucherInformation)
        return bean
    }

    @Override
    Voucher fromBean(VoucherCommand bean) {

        Voucher domain = super.fromBean(bean)

        domain.voucherInformation = voucherInformationAssembler.fromBean(bean)
        return domain
    }

}
