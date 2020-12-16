package vouchers

import enums.ProductType

class Producto {

    String nombre
    String descripcion
    ProductType type

    static belongsTo = [negocio: Negocio]

    static constraints = {
        nombre                blank: false, nullable: false
        descripcion         blank: false, nullable: true
        type                nullable: false
    }
}