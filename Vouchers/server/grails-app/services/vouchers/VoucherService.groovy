package vouchers

import assemblers.VoucherAssembler
import commands.VoucherCommand
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
}
