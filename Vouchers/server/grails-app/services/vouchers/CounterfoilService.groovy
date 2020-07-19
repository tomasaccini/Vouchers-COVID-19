package vouchers

import grails.gorm.transactions.Transactional
import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException

@Transactional
class CounterfoilService {

    VoucherService voucherService

    // !!!!
    List<Counterfoil> counterfoilDB = []

    // !!!!
    Counterfoil create() {
        println "!!!! CreatingCounterfoil"
        VoucherInformation vi = new VoucherInformation(price: 400, description: "Promo verano", validFrom: new Date('2020/08/01'), validUntil:  new Date('2020/08/15'))
        Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 5)
        counterfoilDB.add(counterfoil)
        return counterfoil
    }

    // !!!!
    List<Counterfoil> getAll() {
        println "gettingCounterfoils"
        return counterfoilDB
    }


    /*
    * Creates voucher from counterfoil
    * it associates voucher to client
    * decrease the quantity of stock
    */
    Voucher createVoucher(Long id, Long clientId) {
        Counterfoil counterfoil = Counterfoil.get(id)
        Client client = Client.get(clientId)
        if (counterfoil.stock <= 0) {
            throw new RuntimeException("Voucher does not have stock")
        }
        Voucher v = voucherService.createVoucher(counterfoil.voucherInformation)
        counterfoil.addToVouchers(v)
        client.addToVouchers(v)
        //TODO: This must be propably an atomic attribute
        counterfoil.stock -= 1
        try {
            counterfoil.save(flush:true, failOnError:true)
            client.save(flush:true, failOnError:true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
        return v
    }

    List<Counterfoil> list(Map args) {
        Counterfoil.list(args)
    }

    /*
    * Activates counterfoil
    * If already active, nothing happens
    */
    def activate(Long id) {
        Counterfoil counterfoil = Counterfoil.get(id)
        if (counterfoil.active) {
            return
        }
        counterfoil.active = true
        counterfoil.save(flush:true)
    }

    /*
    * Deactivates counterfoil
    * If already not active, nothing happens
    */
    def deactivate(Long id) {
        Counterfoil counterfoil = Counterfoil.get(id)
        if (!counterfoil.active) {
            return
        }
        counterfoil.active = false
        counterfoil.save(flush:true)
    }
}
