package vouchers

import org.hibernate.service.spi.ServiceException
import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException

@Transactional
class ClienteService {

    TarifarioService tarifarioService
    VoucherService voucherService

    Voucher buyVoucher(Long clientId, Tarifario counterfoil) {
        Cliente client = Cliente.get(clientId)
        Voucher v = tarifarioService.createVoucher(counterfoil.id, clientId)
        client.addToVouchers(v)
        try {
            client.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
        v
    }

    def retireVoucher(Long id, Voucher v){
        Cliente client = Cliente.get(id)
        if (!client?.vouchers?.contains(v)) {
            throw new RuntimeException("The client is not the owner of the Voucher")
        }
        if (!v.isRetirable()) {
            throw new RuntimeException("Voucher has been already retired or is expired")
        }
        voucherService.retire(v.id)
    }
}
