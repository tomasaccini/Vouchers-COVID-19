package vouchers

import enums.states.VoucherEstado

class Voucher {

    Long id
    InformacionVoucher informacionVoucher
    Date fechaCreacion = new Date()
    VoucherEstado estado = VoucherEstado.Comprado
    Date ultimoCambioEstado = new Date()
    Reclamo reclamo = null
    Cliente cliente
    Talonario talonario

    static constraints = {
        informacionVoucher     nullable: false, blank: false
        fechaCreacion            nullable: false
        estado                  nullable: false, blank: false, default: VoucherEstado.Comprado
        ultimoCambioEstado        nullable: true
        reclamo                nullable: true
        cliente                nullable: false, blank: false
        talonario              nullable: false, blank: false
    }

    boolean estaExpirado(){
        if (informacionVoucher.validoHasta <= new Date()) {
            estado = VoucherEstado.Expirado
            return true
        }
        false
    }

    boolean perteneceAlNegocio(Long negocioId){
        return talonario.negocio.id == negocioId
    }

    Reclamo abrirReclamo(String descripcion) {
        if (reclamoAbierto()) {
            throw new RuntimeException("El voucher ya tiene un reclamo. VoucherId: ${id}")
        }

        if (estado != VoucherEstado.Comprado && estado != VoucherEstado.Canjeado) {
            throw new RuntimeException("El voucher no se puede estando en el estado: ${estado}. VoucherId: ${id}")
        }

        if (reclamo == null) {
            reclamo = new Reclamo(voucher: this, descripcion: descripcion)
        } else {
            reclamo.reabrir()
        }

        reclamo.agregarMensaje(descripcion, cliente)

        reclamo.save(flush: true, failOnError: true)
        this.save(flush: true, failOnError: true)

        reclamo
    }

    Boolean reclamoAbierto() {
        return reclamo != null && !reclamo.estaCerrado()
    }

    void confirmarCanje(Negocio negocioConfirmador) {
        def negocio = talonario.negocio.id
        if (negocio != negocioConfirmador.id) {
            throw new RuntimeException("El negocio " + negocio + " no puede confirmar el canje")
        }

        if (!esConfirmable()) {
            throw new RuntimeException("El voucher " + id + " no puede ser confirmado")
        }

        estado = VoucherEstado.Canjeado
        ultimoCambioEstado = new Date()
        save(flush: true, failOnError: true)
    }

    void solicitarCanje(Cliente clienteSolicitante) {
        if (cliente.id != clienteSolicitante.id) {
            throw new RuntimeException("El cliente " + clienteId + " no puede solicitar el canje. Solo el cliente que creo el voucher puede hacerlo")
        }

        if (!esCanjeable()) {
            throw new RuntimeException("El voucher " + id + " no puede ser canjeado")
        }

        estado = VoucherEstado.ConfirmacionPendiente
        ultimoCambioEstado = new Date()
        save(flush: true, failOnError: true)
    }

    void cancelarSolicitudDeCanje(Cliente clienteSolicitante) {
        if (cliente.id != clienteSolicitante.id) {
            throw new RuntimeException("El cliente " + clienteId + " no puede cancelar la solicitud el canje. Solo el cliente que creo el voucher puede hacerlo")
        }

        if (!esConfirmable()) {
            throw new RuntimeException("La solicitud del canje del voucher " + id + " no puede ser cancelada")
        }

        estado = VoucherEstado.Comprado
        ultimoCambioEstado = new Date()
        save(flush: true, failOnError: true)
    }

    private boolean esCanjeable() {
        return !estaExpirado() && estado == VoucherEstado.Comprado && !reclamoAbierto()
    }

    private boolean esConfirmable() {
        return estado == VoucherEstado.ConfirmacionPendiente
    }
}
