package commands

import grails.validation.Validateable
import vouchers.Talonario

class TalonarioCommand implements Validateable {

    Long id
    Long version
    InformacionVoucherCommand voucherInformationCommand
    Boolean activo
    Integer stock
    String nombre
    Integer cantidadVendida

    static constraints = {
        importFrom Talonario
    }
}
