package vouchers

import enums.states.VoucherState
import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException
import java.text.SimpleDateFormat

class Voucher {

    InformacionVoucher informacionVoucher
    Date dateCreated = new Date()
    VoucherState state = VoucherState.Comprado
    Date lastStateChange = new Date()
    Reclamo reclamo = null
    Cliente cliente
    Tarifario tarifario

    static constraints = {
        informacionVoucher     nullable: false, blank: false
        dateCreated            nullable: false
        state                  nullable: false, blank: false, default: VoucherState.Comprado
        lastStateChange        nullable: true
        reclamo                nullable: true
        cliente                nullable: false, blank: false
        tarifario              nullable: false, blank: false
    }

    boolean esRetirable() {
        return !estaExpirado() && state == VoucherState.Comprado
    }

    boolean esConfirmable() {
        return state == VoucherState.ConfirmacionPendiente
    }

    boolean estaExpirado(){
        return informacionVoucher.validoHasta >= new Date()
    }

    boolean perteneceAlNegocio(Long negocioId){
        return tarifario.negocio.id == negocioId
    }

    Reclamo iniciarReclamo(String descripcion) {
        if (reclamo != null) {
            throw new RuntimeException("El voucher ya tiene un reclamo. VoucherId: ${id}")
        }
        Reclamo nuevoReclamo = new Reclamo(voucher: this, cliente: cliente, negocio: tarifario.negocio, descripcion: descripcion)

        nuevoReclamo.agregarMensaje(descripcion, cliente)
        reclamo = nuevoReclamo

        nuevoReclamo.save(flush: true, failOnError: true)
        this.save(flush: true, failOnError: true)

        nuevoReclamo
    }
}
