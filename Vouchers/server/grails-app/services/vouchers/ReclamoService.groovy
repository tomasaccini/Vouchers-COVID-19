package vouchers

import enums.states.ReclamoEstado
import grails.gorm.transactions.Transactional

@Transactional
class ReclamoService {

    UsuarioService usuarioService = new UsuarioService()

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

    Reclamo abrirReclamo(Long voucherId, String descripcion) {
        println("RecalmoService.abrirReclamo(${voucherId}, ${descripcion})")

        Voucher voucher = Voucher.findById(voucherId)

        if (voucher == null) {
            throw new RuntimeException('No se puede crear un reclamo por un voucher invalido')
        }

        Reclamo nuevoReclamo = voucher.abrirReclamo(descripcion)

        println("RecalmoService.abrirReclamo() => ${nuevoReclamo}")

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
            throw new RuntimeException("El usuario " + usuarioId + " no puede cerrar este. Solo el cliente " + reclamo.getVoucher().getCliente().id + " que creo el reclamo puede hacerlo")
        }

        reclamo.estado = ReclamoEstado.Cerrado
        reclamo.save(flush: true, failOnError: true)


        return reclamo
    }

    private List<Reclamo> _obtenerPorUsuario(Cliente cliente) {
        String query = "select r from Reclamo as r "
        query += " join r.voucher as voucher "
        query += " where voucher.cliente.id like :clienteId "
        List<Reclamo> reclamos = Reclamo.executeQuery(query, [clienteId: cliente.id], [:])

        return reclamos
    }

    private List<Reclamo> _obtenerPorUsuario(Negocio negocio) {
        String query = "select r from Reclamo as r "
        query += " join r.voucher as voucher "
        query += " where voucher.talonario.negocio.id like :negocioId "
        List<Reclamo> reclamos = Reclamo.executeQuery(query, [negocioId: negocio.id], [:])

        return reclamos
    }

    private def agregarMensaje(Reclamo reclamo, Cliente duenio, String mensaje) {
        reclamo.agregarMensaje(mensaje, duenio)
    }

    private def agregarMensaje(Reclamo reclamo, Negocio duenio, String mensaje) {
        reclamo.agregarMensaje(mensaje, duenio)
    }
}
