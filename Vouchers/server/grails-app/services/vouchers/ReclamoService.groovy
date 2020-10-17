package vouchers


import grails.gorm.transactions.Transactional

@Transactional
class ReclamoService {

    def crearReclamo(Long voucherId, String descripcion) {
        Voucher voucher = Voucher.findById(voucherId)
        new Reclamo(cliente: voucher.cliente, negocio: voucher.tarifario.negocio, descripcion: descripcion)
    }
}
