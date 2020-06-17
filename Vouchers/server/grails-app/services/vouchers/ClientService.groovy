package vouchers

import org.hibernate.service.spi.ServiceException
import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException

@Transactional
class ClientService {

    CounterfoilService counterfoilService
    VoucherService voucherService

    Voucher buyVoucher(Long id, Counterfoil c) {
        Client client = Client.get(id)
        Voucher v = counterfoilService.createVoucher(c.id, id)
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
        voucherService.retire()
    }
}
