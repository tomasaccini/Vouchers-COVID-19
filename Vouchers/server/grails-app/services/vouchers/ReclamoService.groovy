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
        println("RecalmoService.crearReclamo(${voucherId}, ${descripcion})")

        Voucher voucher = Voucher.findById(voucherId)

        if (voucher == null) {
            throw new RuntimeException('No se puede crear un reclamo por un voucher invalido')
        }

        Reclamo nuevoReclamo = voucher.iniciarReclamo(descripcion)

        println("RecalmoService.crearReclamo() => ${nuevoReclamo}")

        nuevoReclamo
    }

    def nuevoMensaje(Long reclamoId, Long usuarioId, String mensaje) {
        Reclamo reclamo = Reclamo.findById(reclamoId)
        if (reclamo == null) {
            throw new RuntimeException("No se puede crear un nuevo mensaje a un reclamo que no existe. ReclamoId: ${reclamoId}")
        }

        Usuario usuario = usuarioService.obtener(usuarioId)

        agregarMensaje(reclamo, usuario, mensaje)

        return reclamo
    }

    List<Reclamo> obtenerPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioService.obtener(usuarioId)
        List<Reclamo> reclamos =  _obtenerPorUsuario(usuario)

        reclamos.sort { - it.fechaUltimoMensaje.seconds }
    }

    def cerrarReclamo(Long reclamoId, usuarioId) {
        Reclamo reclamo = Reclamo.get(reclamoId)
        Usuario usuario = Usuario.get(usuarioId)
        reclamo.cerrar(usuario)
        return reclamo
    }

    private List<Reclamo> _obtenerPorUsuario(Cliente cliente) {
        List<Reclamo> reclamos = Reclamo.findAllByCliente(cliente)

        return reclamos
    }

    private List<Reclamo> _obtenerPorUsuario(Negocio negocio) {
        List<Reclamo> reclamos = Reclamo.findAllByNegocio(negocio)

        return reclamos
    }

    private def agregarMensaje(Reclamo reclamo, Cliente duenio, String mensaje) {
        reclamo.agregarMensaje(mensaje, duenio)
    }

    private def agregarMensaje(Reclamo reclamo, Negocio duenio, String mensaje) {
        reclamo.agregarMensaje(mensaje, duenio)
    }
}
