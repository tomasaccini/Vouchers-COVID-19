package commands

import enums.ProductType
import grails.validation.Validateable
import vouchers.Producto

class ProductoCommand implements Validateable {

    Long id
    Long version
    String name
    String description
    ProductType type

    static constraints = {
        importFrom Producto
    }
}
