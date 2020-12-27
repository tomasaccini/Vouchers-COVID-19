package commands

import grails.validation.Validateable
import vouchers.Direccion
import vouchers.Talonario

class TalonarioCommand implements Validateable {

    Long id
    Long version
    InformacionVoucherCommand informacionVoucherCommand
    Boolean activo
    Integer stock
    String nombre
    Integer cantidadVendida

    static constraints = {
        importFrom Talonario
    }
}
