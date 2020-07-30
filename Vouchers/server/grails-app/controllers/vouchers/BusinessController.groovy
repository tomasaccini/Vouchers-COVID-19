package vouchers

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*

@Secured(['ROLE_BUSINESS'])
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
        println("Request for a business by id")
        if (!business){
            response.sendError(404)
        } else {
            respond business
        }
    }

    /*
    * Returns counterfoils requested by Id
    * URL/businesses/{id}
    */
    def getCounterfoils(Business business){
        println("Request counterfoils of business")
        if (!business){
            response.sendError(404)
        } else {
            respond business.counterfoils
        }
    }

    /*
    * Returns list of businesses. If specified, it returns a max amount of them.
    * URL/businesses -> When max is not specified
    * URL/businesses?max=n" -> When max is specified
    */
    def index(Integer max){
        println("Request for business list, max size: ${params.max}")
        params.max = Math.min(max ?: 10, 100)
        respond Business.list(params)
    }

}
