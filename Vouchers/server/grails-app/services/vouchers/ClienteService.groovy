package vouchers

import grails.gorm.transactions.Transactional

@Transactional
class ClienteService {

    TalonarioService talonarioService
    VoucherService voucherService

    Voucher comprarVoucher(Long clienteId, Long talonarioId) {
        println("ClienteService.comprarVoucher(${clienteId}, ${talonarioId})")

        Talonario talonario = talonarioService.get(talonarioId)
        Cliente cliente = Cliente.get(clienteId)

        Voucher voucher = talonario.comprarVoucher(cliente)

        println("ClienteService.comprarVoucher() => ${voucher}")

        voucher
    }

    def retirarVoucher(Long id, Voucher voucher) {
        Cliente cliente = Cliente.get(id)
        if (!cliente?.vouchers?.contains(voucher)) {
            throw new RuntimeException("El cliente no es el duenio del voucher")
        }
        if (!voucher.esCanjeable()) {
            throw new RuntimeException("El voucher ya fue retirado o esta expirado")
        }
        voucherService.solicitarCanje(voucher.id, id)
    }

    Cliente obtener(Long clienteId) {
        def cliente = Cliente.findById(clienteId)
        return cliente
    }

    List<Cliente> obtenerTodos() {
        return Cliente.findAll()
    }
}
