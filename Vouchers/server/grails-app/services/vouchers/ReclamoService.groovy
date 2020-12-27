package vouchers

import enums.states.ReclamoState
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

    Reclamo iniciarReclamo(Long voucherId, String descripcion) {
        println("RecalmoService.iniciarReclamo(${voucherId}, ${descripcion})")

        Voucher voucher = Voucher.findById(voucherId)

        if (voucher == null) {
            throw new RuntimeException('No se puede crear un reclamo por un voucher invalido')
        }

        Reclamo nuevoReclamo = voucher.iniciarReclamo(descripcion)

        println("RecalmoService.iniciarReclamo() => ${nuevoReclamo}")

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
        List<Reclamo> reclamos = _obtenerPorUsuario(usuario)

        reclamos.sort { -it.fechaUltimoMensaje.seconds }
    }

    def cerrarReclamo(Long reclamoId, usuarioId) {
        Reclamo reclamo = Reclamo.get(reclamoId)
        Usuario usuario = Usuario.get(usuarioId)

        // El codigo esta aca en vez de en reclamo.cerrar() porque no se actualizaba la DB cuando se pone ahi. Cosa rara de grails.
        // reclamo.cerrar(usuario)

        if (reclamo.estaCerrado()) {
            throw new RuntimeException("El reclamo ya fue cerrado")
        }

        if (!reclamo.perteneceAUsuario(usuario)) {
            throw new RuntimeException("El usuario " + usuarioId + " no puede cerrar este. Solo el cliente que creo el reclamo puede hacerlo")
        }

        reclamo.state = ReclamoState.Cerrado
        reclamo.save(flush: true, failOnError: true)


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
