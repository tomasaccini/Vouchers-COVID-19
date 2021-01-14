package commands

import grails.validation.Validateable
import vouchers.Talonario

class TalonarioCommand implements Validateable {

    Long id
    Long version
    InformacionVoucherCommand informacionVoucherCommand
    Boolean activo
    Integer stock
    String negocioNombre
    String negocioId
    Integer cantidadVendida

    static constraints = {
        importFrom Talonario
    }
}
