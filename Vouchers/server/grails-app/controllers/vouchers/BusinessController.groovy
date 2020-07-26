package vouchers


import grails.rest.*

class BusinessController extends RestfulController {

    static responseFormats = ['json', 'xml']

    BusinessController() {
        super(Business)
    }

    /*
    * Returns business requested by Id
    * URL/businesses/{id}
    */
    def show(Business business){
        println("Asking for a business")
        if (!business){
            response.sendError(404)
        } else {
            respond business
        }
    }

    /*
    * Returns list of businesses. If specified, it returns a max amount of them.
    * URL/businesses -> When max is not specified
    * URL/businesses?max=n" -> When max is specified
    */
    def index(Integer max){
        println("Asking for business list, maz size: ${params.max}")
        params.max = Math.min(max ?: 10, 100)
        respond Business.list(params)
    }

}
