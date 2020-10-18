package vouchers


import grails.gorm.transactions.Transactional

@Transactional
class ReclamoService {

    List<Reclamo> obtenerTodos() {
        return Reclamo.findAll()
    }

    Reclamo obtener(Long reclamoId) {
        return Reclamo.findById(reclamoId)
    }

    Reclamo crearReclamo(Long voucherId, String descripcion) {
        Voucher voucher = Voucher.findById(voucherId)

        if (voucher == null) {
            throw new RuntimeException('No se puede crear un reclamo por un voucher invalido')
        }

        Reclamo nuevoReclamo = voucher.iniciarReclamo(descripcion)

        nuevoReclamo
    }

    def reclamosPorUsuario(Long usuarioId) {
        // !!!! Chequear si es cliente o negocio
        Negocio negocio = Negocio.findById(usuarioId)

        List<Reclamo> reclamos = Reclamo.findAllByNegocio(negocio)

        return reclamos
    }

    def nuevoMensaje(Long reclamoId, Long usuarioId, String mensaje) {
        Reclamo reclamo = Reclamo.findById(reclamoId)

        // !!!! Chequear si es cliente o negocio
        Negocio negocio = Negocio.findById(usuarioId)

        reclamo.agregarMensaje(mensaje, negocio)
        reclamo.save()

        return reclamo
    }

    // !!!! cerrarReclamo
}
