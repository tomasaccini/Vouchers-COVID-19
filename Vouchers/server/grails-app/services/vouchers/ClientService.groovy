package vouchers

import org.hibernate.service.spi.ServiceException
import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException

@Transactional
class ClientService {

    CounterfoilService counterfoilService
    VoucherService voucherService

    Voucher buyVoucher(Long clientId, Counterfoil counterfoil) {
        Client client = Client.get(clientId)
        Voucher v = counterfoilService.createVoucher(counterfoil.id, clientId)
        client.addToVouchers(v)
        try {
            client.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
        v
    }

    def retireVoucher(Long id, Voucher v){
        Client client = Client.get(id)
        if (!client?.vouchers?.contains(v)) {
            throw new RuntimeException("The client is not the owner of the Voucher")
        }
        if (!v.isRetirable()) {
            throw new RuntimeException("Voucher has been already retired or is expired")
        }
        voucherService.retire(v.id)
    }
}
