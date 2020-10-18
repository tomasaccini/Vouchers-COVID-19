package vouchers

import assemblers.VoucherAssembler
import commands.VoucherCommand
import enums.states.VoucherState
import grails.gorm.transactions.Transactional
import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException

@Transactional
class VoucherService {

    VoucherAssembler voucherAssembler

    def save(VoucherCommand voucherCommand) {
        Voucher voucher = voucherAssembler.fromBean(voucherCommand)
        try {
            voucher.save(flush: true, failOnError: true)
        } catch (ValidationException e) {
            throw new ServiceException(e.message)
        }
        return voucher
    }

    def update(VoucherCommand voucherCommand) {
        Voucher voucher = voucherAssembler.fromBean(voucherCommand)
        try {
            voucher.save(flush: true, failOnError: true)
        } catch (ValidationException e) {
            throw new ServiceException(e.message)
        }
        return voucher
    }

    def delete(Long id) {
        try {
            Voucher voucher = Voucher.get(id)
            voucher.delete(flush: true, failOneEror: true)
        } catch (ServiceException e) {
            log.error(e)
            throw e
        }
    }

    def confirm(Long id) {
        Voucher voucher = Voucher.get(id)
        if (!voucher.esConfirmable()) {
            //TODO: Throw Excepction
            return
        }
        modifyState(voucher, VoucherState.Retirado)
    }

    def retire(Long id) {
        Voucher voucher = Voucher.get(id)
        if (!voucher.esRetirable()) {
            //TODO: Throw Excepction
            return
        }
        modifyState(voucher, VoucherState.ConfirmacionPendiente)
    }

    def modifyState(Voucher voucher, VoucherState newState) {
        voucher.state = newState
        voucher.lastStateChange = new Date()
        try {
            voucher.save(flush: true, failOnError: true)
        } catch (ValidationException e) {
            throw new ServiceException(e.message)
        }
    }

    List<Voucher> list() {
        return mockVoucherList()
    }

    List<Voucher> listByUserId(String userId) {
        return mockVoucherList()
    }

    private List<Voucher> mockVoucherList() {
        InformacionVoucher vi = createVoucherInformation()
        Voucher v = new Voucher(informacionVoucher: vi)
        return [v]
    }

    private InformacionVoucher createVoucherInformation(valid_until = new Date('2020/12/31')) {
        Producto p1 = new Producto(nombre:"Hamburguesa", descripcion: "Doble cheddar")
        Producto p2 = new Producto(nombre:"Pinta cerveza", descripcion: "Cerveza artesanal de la casa")
        Item i1 = new Item(producto: p1, cantidad: 1)
        Item i2 = new Item(producto: p2, cantidad: 2)

        InformacionVoucher vi = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: valid_until, items: [i1, i2])
        vi
    }
}
