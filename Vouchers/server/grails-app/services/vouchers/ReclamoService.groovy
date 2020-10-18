package vouchers


import grails.gorm.transactions.Transactional

@Transactional
class ReclamoService {

    UsuarioService usuarioService

    List<Reclamo> obtenerTodos() {
        return Reclamo.findAll()
    }

    Reclamo obtener(Long reclamoId) {
        Reclamo reclamo = Reclamo.findById(reclamoId)

        if (reclamo == null) {
            throw new RuntimeException("No se encontro reclamo con ReclamoId: ${reclamoId}")
        }

        return reclamo
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
        // !!!!! Chequear si es cliente o negocio
        Negocio negocio = Negocio.findById(usuarioId)

        List<Reclamo> reclamos = Reclamo.findAllByNegocio(negocio)

        return reclamos
    }

    def nuevoMensaje(Long reclamoId, Long usuarioId, String mensaje) {
        Reclamo reclamo = Reclamo.findById(reclamoId)
        if (reclamo == null) {
            throw new RuntimeException("No se puede crear un nuevo mensaje a un reclamo que no existe. ReclamoId: ${reclamoId}")
        }

        Usuario usuario = usuarioService.obtener(usuarioId)

        reclamo.agregarMensaje(mensaje, usuario)

        return reclamo
    }

    // !!!! cerrarReclamo
}
