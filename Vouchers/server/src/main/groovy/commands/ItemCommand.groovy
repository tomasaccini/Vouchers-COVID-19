package commands

import grails.validation.Validateable
import vouchers.Item

class ItemCommand implements Validateable {

    Long id
    Long version
    ProductoCommand productCommand
    Integer quantity

    static constraints = {
        importFrom Item
    }

}
