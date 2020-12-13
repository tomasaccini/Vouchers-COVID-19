package commands

import grails.validation.Validateable
import vouchers.InformacionVoucher

class InformacionVoucherCommand implements Validateable{

    Long id
    Long version
    Double precio
    String descripcion
    Date validoDesde
    Date validoHasta
    List itemsCommand

    static constraints = {
        importFrom InformacionVoucher
    }
}
