package assemblers

import commands.VoucherCommand
import templates.ConcreteObjectAssembler
import vouchers.Voucher

class VoucherAssembler extends ConcreteObjectAssembler<Voucher, VoucherCommand>{

    VoucherInformationAssembler voucherInformationAssembler
    NegocioAssembler negocioAssembler

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

        if (domain.informacionVoucher){
            bean.informacionVoucherCommand = voucherInformationAssembler.toBean(domain.informacionVoucher)
        }

        bean.negocioCommand = negocioAssembler.toBean(domain.talonario.negocio)
        bean.enReclamo = domain.reclamo != null

        return bean
    }

    @Override
    Voucher fromBean(VoucherCommand bean) {

        Voucher domain = super.fromBean(bean)

        if (bean.info){
            domain.informacionVoucher = voucherInformationAssembler.fromBean(bean.info)
        }

        return domain
    }
}
