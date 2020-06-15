package commands

import grails.validation.Validateable
import vouchers.Product

class ProductCommand implements Validateable {

    Long id
    Long version
    String name
    String description

    static constraints = {
        importFrom Product
    }
}
