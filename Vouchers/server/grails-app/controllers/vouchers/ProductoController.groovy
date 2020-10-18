package vouchers


import grails.rest.*

class ProductoController extends RestfulController {

	static responseFormats = ['json', 'xml']

    ProductoController(){
        super(Producto)
    }

    /*
    * Returns list of businesses's products. If specified, it returns a max amount of them.
    * URL/products/getByBusiness/businessId -> When max is not specified
    * URL/products/getByBusiness/businessId?max=n -> When max is specified
    */
    def getByBusiness(Long businessId, Integer max){
        Negocio business = Negocio.get(businessId)
        params.max = Math.min(max ?: 10, 100)
        if (!business){
            // TODO: Mejores mensajes de error / no encontrado
            response.sendError(404)
        }
        println("Request for business products, businessid: ${business?.id}")
        respond Producto.findAllByBusiness(business)
    }

    /*
    * Returns product requested by Id
    * URL/products/{id}
    */
    def show(Producto product){
        println("Request for a product by id")
        if (!product){
            response.sendError(404)
        } else {
            respond product
        }
    }


	
}