package vouchers

import enums.ProductoTipo

class Producto {

    String nombre
    String descripcion
    ProductoTipo tipo

    static belongsTo = [negocio: Negocio]

    static constraints = {
        nombre                blank: false, nullable: false
        descripcion         blank: false, nullable: true
        tipo                nullable: false
    }
}