package assemblers

import commands.VoucherCommand
import templates.ConcreteObjectAssembler
import vouchers.Voucher

class VoucherAssembler extends ConcreteObjectAssembler<Voucher, VoucherCommand> {

    InformacionVoucherAssembler informacionVoucherAssembler
    ReclamoAssembler reclamoAssembler
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

        if (domain.informacionVoucher) {
            bean.informacionVoucherCommand = informacionVoucherAssembler.toBean(domain.informacionVoucher)
        }

        bean.negocioCommand = negocioAssembler.toBean(domain.talonario.negocio)
        bean.reclamoAbierto = domain.reclamoAbierto()
        bean.reclamoId = domain.reclamoAbierto() ? domain.reclamo.id : null
        bean.reclamoCommand = domain.reclamo != null ? reclamoAssembler.toBean(domain.reclamo) : null
        bean.clienteEmail = domain.cliente.email

        return bean
    }

    @Override
    Voucher fromBean(VoucherCommand bean) {

        Voucher domain = super.fromBean(bean)

        if (bean.info) {
            domain.informacionVoucher = informacionVoucherAssembler.fromBean(bean.info)
        }

        return domain
    }
}
