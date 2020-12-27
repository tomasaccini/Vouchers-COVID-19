package vouchers

import enums.states.VoucherState
import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException

class Voucher {

    Long id
    InformacionVoucher informacionVoucher
    Date dateCreated = new Date()
    VoucherState state = VoucherState.Comprado
    Date lastStateChange = new Date()
    Reclamo reclamo = null
    Cliente cliente
    Talonario talonario

    static constraints = {
        informacionVoucher     nullable: false, blank: false
        dateCreated            nullable: false
        state                  nullable: false, blank: false, default: VoucherState.Comprado
        lastStateChange        nullable: true
        reclamo                nullable: true
        cliente                nullable: false, blank: false
        talonario              nullable: false, blank: false
    }

    boolean estaExpirado(){
        return informacionVoucher.validoHasta >= new Date()
    }

    boolean perteneceAlNegocio(Long negocioId){
        return talonario.negocio.id == negocioId
    }

    Reclamo iniciarReclamo(String descripcion) {
        println(reclamo)

        if (enReclamo()) {
            throw new RuntimeException("El voucher ya tiene un reclamo. VoucherId: ${id}")
        }

        if (reclamo == null) {
            reclamo = new Reclamo(voucher: this, cliente: cliente, negocio: talonario.negocio, descripcion: descripcion)
        } else {
            reclamo.reabrir()
        }

        reclamo.agregarMensaje(descripcion, cliente)

        reclamo.save(flush: true, failOnError: true)
        this.save(flush: true, failOnError: true)

        reclamo
    }

    Boolean enReclamo() {
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

        state = VoucherState.Retirado
        lastStateChange = new Date()
        save(flush: true, failOnError: true)
    }

    void solicitarCanje(Cliente clienteSolicitante) {
        if (cliente.id != clienteSolicitante.id) {
            throw new RuntimeException("El cliente " + clienteId + " no puede solicitar el canje. Solo el cliente que creo el voucher puede hacerlo")
        }

        if (!esCanjeable()) {
            throw new RuntimeException("El voucher " + id + " no puede ser canjeado")
        }

        state = VoucherState.ConfirmacionPendiente
        lastStateChange = new Date()
        save(flush: true, failOnError: true)
    }

    // TODO: deberia ser privado !!!!
    boolean esCanjeable() {
        return !estaExpirado() && state == VoucherState.Comprado
    }

    boolean esConfirmable() {
        return state == VoucherState.ConfirmacionPendiente
    }
}
