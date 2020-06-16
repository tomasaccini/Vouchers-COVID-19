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
        Voucher voucher = voucherAssembler.toDomain(voucherCommand)
        try {
            voucher.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
        return voucher
    }

    def update(VoucherCommand voucherCommand) {
        Voucher voucher = voucherAssembler.toDomain(voucherCommand)
        try {
            voucher.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
        return voucher
    }

    def addItem(Long id, Item item){
        Voucher voucher = Voucher.get(id)
        voucher.addToItems(item)
        try {
            voucher.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
    }


    def delete(Long id) {
        try {
            Voucher voucher = Voucher.get(id)
            voucher.items.clear()
            voucher.delete(flush: true, failOneEror: true)
        } catch (ServiceException e) {
            log.error(e)
            throw e
        }
    }
}
