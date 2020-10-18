package vouchers

import grails.gorm.transactions.Transactional
import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException

@Transactional
class NegocioService {

    VoucherService voucherService
    TarifarioService tarifarioService

    /*
    * Gets a product and adds it to business
    */
    void addProduct(Long id, Producto p) {
        Negocio business = Negocio.get(id)
        business.addToProducts(p)
        try {
            business.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
    }

    void removeProduct(Long id, Producto p){
        Negocio business = Negocio.get(id)
        business.removeFromProducts(p)
        try {
            business.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
    }

    /*
    * Gets a counterfoil and adds it to business
    */
    void addCounterfoil(Long id, Tarifario c) {
        Negocio business = Negocio.get(id)
        business.addToTarifarios(c)
        try {
            business.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
    }

    def confirmRetireVoucher(Long id, Voucher voucher){
        Negocio business = Negocio.get(id)
        if (!business.isOwnerOfVoucher(voucher)) {
            throw new RuntimeException("The business is not the owner of the Voucher")
        }
        if (!voucher.esConfirmable()) {
            throw new RuntimeException("Voucher is not confirmable")
        }
        voucherService.confirm(voucher.id)
    }

    /*
    * Activates counterfoil
    * When activated, vouchers can be purchased
    */
    boolean activateCounterfoil(Long id, Long counterfoilId) {
        Negocio business = Negocio.get(id)
        if (!business.isOwnerOfCounterfoil(counterfoilId)) {
            throw new RuntimeException("The business is not the owner of the Counterfoil")
        }
        tarifarioService.activate(counterfoilId)
    }

    /*
    * Deactivates counterfoil
    * When deactivated, vouchers can't be purchased
    */
    boolean deactivateCounterfoil(Long id, Long counterfoilId) {
        Negocio business = Negocio.get(id)
        if (!business.isOwnerOfCounterfoil(counterfoilId)) {
            throw new RuntimeException("The business is not the owner of the Counterfoil")
        }
        tarifarioService.deactivate(counterfoilId)
    }

    List<Negocio> obtenerTodos() {
        def a = Negocio.findAll()
        println("\n\n\n\n\n !!!! ${a}")
        a
    }
}