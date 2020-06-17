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
            voucher.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
        return voucher
    }

    def update(VoucherCommand voucherCommand) {
        Voucher voucher = voucherAssembler.fromBean(voucherCommand)
        try {
            voucher.save(flush:true, failOnError: true)
        } catch (ValidationException e){
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

    def modifyState(Voucher voucher, VoucherState newState){
        voucher.state = newState
        voucher.lastStateChange = new Date()
        try {
            voucher.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
    }

    Voucher createVoucher(VoucherInformation vi){
        Voucher voucher = new Voucher()
        voucher.voucherInformation = vi.duplicate()
        try {
            voucher.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
    }
}
