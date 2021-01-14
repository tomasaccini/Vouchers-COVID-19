package vouchers

import enums.ProductoTipo
import grails.gorm.transactions.Transactional
import grails.rest.RestfulController

class ProductoController extends RestfulController {

    static responseFormats = ['json', 'xml']

    ProductoController() {
        super(Producto)
    }

    /*
    * Returns list of businesses's products. If specified, it returns a max amount of them.
    * URL/products/obtenerPorNegocio/negocioId -> When max is not specified
    * URL/products/obtenerPorNegocio/negocioId?max=n -> When max is specified
    */

    def obtenerPorNegocio(Long negocioId, Integer max) {
        Negocio negocio = Negocio.get(negocioId)
        params.max = Math.min(max ?: 10, 100)
        if (!negocio) {
            return response.sendError(400, "Negocio inexistente")
        }
        println("Request for business products, businessid: ${negocio?.id}")
        respond Producto.findAllByNegocio(negocio)
    }

    /*
    * Returns product requested by Id
    * URL/products/{id}
    */

    def show(Producto product) {
        println("Request for a product by id")
        if (!product) {
            response.sendError(404)
        } else {
            respond product
        }
    }

    /*
    * Crea producto para el negocio especificado
    * URL/productos/crear
    */
    @Transactional
    def crear() {
        // REFACTOR
        println("Creando producto")
        Object requestBody = request.JSON
        Producto nProducto = new Producto()
        try {
            nProducto.setNombre(requestBody['nombre'].toString())
            nProducto.setDescripcion(requestBody['descripcion'].toString())
            Negocio negocio = Negocio.get(requestBody['negocioId'].toLong())
            nProducto.setNegocio(negocio)
            nProducto.setTipo(ProductoTipo.FAST_FOOD) // cambiar
            nProducto.save()
            negocio.addToProducts(nProducto)
            negocio.save()
        } catch (Exception e) {
            println(e)
            return response.sendError(400, "Problems in format")
        }
        respond nProducto
    }
}
