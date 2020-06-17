package commands

import grails.validation.Validateable
import vouchers.Item
import vouchers.VoucherInformation

class VoucherInformationCommand  implements Validateable{

    Long id
    Long version
    Double price
    String description
    Date validFrom
    Date validUntil
    List itemsCommand

    static constraints = {
        importFrom VoucherInformation
    }
}
