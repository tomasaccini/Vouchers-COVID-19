package vouchers

import assemblers.VoucherAssembler
import assemblers.VoucherInformationAssembler
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
        if (!voucher.isConfirmable()) {
            //TODO: Throw Excepction
            return
        }
        modifyState(voucher, VoucherState.RETIRED)
    }

    def retire(Long id) {
        Voucher voucher = Voucher.get(id)
        if (!voucher.isRetirable()) {
            //TODO: Throw Excepction
            return
        }
        modifyState(voucher, VoucherState.PENDING_CONFIRMATION)
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

    Voucher createVoucher(VoucherInformation vi) {
        Voucher voucher = new Voucher()
        voucher.voucherInformation = vi
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
        VoucherInformation vi = createVoucherInformation()
        Voucher v = new Voucher(voucherInformation: vi)
        return [v]
    }

    private VoucherInformation createVoucherInformation(valid_until = new Date('2020/12/31')) {
        Product p1 = new Product(name:"Hamburguesa", description: "Doble cheddar")
        Product p2 = new Product(name:"Pinta cerveza", description: "Cerveza artesanal de la casa")
        Item i1 = new Item(product: p1, quantity: 1)
        Item i2 = new Item(product: p2, quantity: 2)

        VoucherInformation vi = new VoucherInformation(price: 400, description: "Promo verano", validFrom: new Date('2020/08/01'), validUntil: valid_until, items: [i1, i2])
        vi
    }
}
