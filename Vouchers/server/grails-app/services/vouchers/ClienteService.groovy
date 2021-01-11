package vouchers

import enums.states.VoucherEstado
import grails.gorm.transactions.Transactional

@Transactional
class ClienteService {

    TalonarioService talonarioService
    VoucherService voucherService

    // TODO mover a talonarioController? !!!!
    Voucher comprarVoucher(Long clienteId, Long talonarioId) {
        println("ClienteService.comprarVoucher(${clienteId}, ${talonarioId})")

        Talonario talonario = talonarioService.get(talonarioId)
        Cliente cliente = Cliente.get(clienteId)

        Voucher voucher = talonario.comprarVoucher(cliente)

        println("ClienteService.comprarVoucher() => ${voucher}")

        voucher
    }

    def retirarVoucher(Long id, Voucher voucher){
        Cliente client = Cliente.get(id)
        if (!client?.vouchers?.contains(voucher)) {
            throw new RuntimeException("El cliente no es el duenio del voucher")
        }
        if (!voucher.esCanjeable()) {
            throw new RuntimeException("El voucher ya fue retirado o esta expirado")
        }
        voucherService.solicitarCanje(voucher.id, id)
    }
}
