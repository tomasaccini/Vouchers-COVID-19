package vouchers

import org.hibernate.service.spi.ServiceException
import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException

@Transactional
class ClienteService {

    TarifarioService tarifarioService
    VoucherService voucherService

    // TODO mover a tarifarioController? !!!!
    Voucher comprarVoucher(Long clienteId, Long tarifarioId) {
        println("ClienteService.comprarVoucher(${clienteId}, ${tarifarioId})")

        Tarifario tarifario = tarifarioService.get(tarifarioId)
        Cliente cliente = Cliente.get(clienteId)

        Voucher voucher = tarifario.crearVoucher(cliente)

        println("ClienteService.comprarVoucher() => ${voucher}")

        voucher
    }

    def retirarVoucher(Long id, Voucher voucher){
        Cliente client = Cliente.get(id)
        if (!client?.vouchers?.contains(voucher)) {
            throw new RuntimeException("The client is not the owner of the Voucher")
        }
        if (!voucher.esRetirable()) {
            throw new RuntimeException("Voucher has been already retired or is expired")
        }
        voucherService.retire(voucher.id)
    }
}
