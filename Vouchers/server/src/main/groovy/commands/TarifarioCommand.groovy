package commands

import grails.validation.Validateable
import vouchers.Tarifario

class TarifarioCommand implements Validateable {

    Long id
    Long version
    InformacionVoucherCommand voucherInformationCommand
    Boolean active
    Integer stock
    String businessName

    static constraints = {
        importFrom Tarifario
    }
}
