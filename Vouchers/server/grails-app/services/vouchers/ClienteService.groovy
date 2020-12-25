package vouchers


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

        Voucher voucher = talonario.crearVoucher(cliente)

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
