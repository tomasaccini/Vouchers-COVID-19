package vouchers

import grails.gorm.transactions.Transactional
import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException

@Transactional
class BusinessService {

    VoucherService voucherService

    void addProduct(Long id, Product p) {
        Business business = Business.get(id)
        business.addToProducts(p)
        try {
            business.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
    }

    void addCounterfoil(Long id, Counterfoil c) {
        Business business = Business.get(id)
        business.addToCounterfoils(c)
        try {
            business.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
    }

    def confirmRetireVoucher(Long id, Voucher voucher){
        Business business = Business.get(id)
        if (!business.isOwnerOfVoucher(voucher)) {
            throw new RuntimeException("The business is not the owner of the Voucher")
        }
        if (!voucher.isConfirmable()) {
            throw new RuntimeException("Voucher is not confirmable")
        }
        voucherService.confirm(voucher.id)
    }
}
