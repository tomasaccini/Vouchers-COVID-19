package commands

import grails.validation.Validateable
import vouchers.Item

class ItemCommand implements Validateable{

    Long id
    Long version
    ProductCommand productCommand
    Integer quantity

    static constraints = {
        importFrom Item
    }

}
