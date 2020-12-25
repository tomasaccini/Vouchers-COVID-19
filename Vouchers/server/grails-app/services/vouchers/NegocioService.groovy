package vouchers

import grails.gorm.transactions.Transactional
import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException

@Transactional
class NegocioService {

    VoucherService voucherService
    TalonarioService talonarioService

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
    void addCounterfoil(Long id, Talonario c) {
        Negocio business = Negocio.get(id)
        business.addToTalonarios(c)
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
        talonarioService.activate(counterfoilId)
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
        talonarioService.deactivate(counterfoilId)
    }

    List<Negocio> obtenerTodos() {
        def a = Negocio.findAll()
        println("\n\n\n\n\n !!!! ${a}")
        a
    }

    List<Negocio> findSimilar(String q, Map map) {
        String query = "select distinct(n) from Negocio as n "
        query += " where lower(n.nombre) like :search "
        Negocio.executeQuery(query, [search: "%${q}%".toLowerCase()] , map)
    }
}
