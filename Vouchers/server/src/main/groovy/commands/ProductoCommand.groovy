package commands

import enums.ProductoTipo
import grails.validation.Validateable
import vouchers.Producto

class ProductoCommand implements Validateable {

    Long id
    Long version
    String nombre
    String descripcion
    ProductoTipo tipo

    static constraints = {
        importFrom Producto
    }
}
