package vouchers

import grails.gorm.transactions.Transactional
import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException

@Transactional
class BusinessService {

    VoucherService voucherService
    CounterfoilService counterfoilService

    /*
    * Gets a product and adds it to business
    */
    void addProduct(Long id, Product p) {
        Business business = Business.get(id)
        business.addToProducts(p)
        try {
            business.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
    }

    /*
    * Gets a counterfoil and adds it to business
    */
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

    /*
    * Activates counterfoil
    * When activated, vouchers can be purchased
    */
    boolean activateCounterfoil(Long id, Long counterfoilId) {
        Business business = Business.get(id)
        if (!business.isOwnerOfCounterfoil(counterfoilId)) {
            throw new RuntimeException("The business is not the owner of the Counterfoil")
        }
        counterfoilService.activate(counterfoilId)
    }

    /*
    * Deactivates counterfoil
    * When deactivated, vouchers can't be purchased
    */
    boolean deactivateCounterfoil(Long id, Long counterfoilId) {
        Business business = Business.get(id)
        if (!business.isOwnerOfCounterfoil(counterfoilId)) {
            throw new RuntimeException("The business is not the owner of the Counterfoil")
        }
        counterfoilService.deactivate(counterfoilId)
    }
}
