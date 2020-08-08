package vouchers


import grails.rest.*

class ProductController extends RestfulController {

	static responseFormats = ['json', 'xml']

    ProductController(){
        super(Product)
    }

    /*
    * Returns list of businesses's products. If specified, it returns a max amount of them.
    * URL/products/getByBusiness/businessId -> When max is not specified
    * URL/products/getByBusiness/businessId?max=n -> When max is specified
    */
    def getByBusiness(Long businessId, Integer max){
        Business business = Business.get(businessId)
        params.max = Math.min(max ?: 10, 100)
        if (!business){
            // TODO: Mejores mensajes de error / no encontrado
            response.sendError(404)
        }
        println("Request for business products, businessid: ${business?.id}")
        respond Product.findAllByBusiness(business)
    }

    /*
    * Returns business requested by Id
    * URL/businesses/{id}
    */
    def show(Product product){
        println("Request for a product by id")
        if (!product){
            response.sendError(404)
        } else {
            respond product
        }
    }


	
}
