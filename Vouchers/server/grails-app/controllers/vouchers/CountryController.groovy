package vouchers


import grails.rest.*

class CountryController extends RestfulController {

	static responseFormats = ['json', 'xml']
	
    CountryController(){
        super(Country)
    }

    /*
    * Returns country requested by Id
    * URL/countries/{id}
    */
    def show(Country country) {
        println("Asking for a country")
        if (!country) {
            response.sendError(404)
        }
        else {
            respond country
        }
    }

    /*
    * Returns list of countries. If specified, it returns a max amount of them.
    * URL/countries -> When max is not specified
    * URL/countries?max=n" -> When max is specified
    */
    def index(Integer max){
        println("Asking for countries list, maz size: ${params.max}")
        params.max = Math.min(max ?: 10, 100)
        respond Country.list(params)
    }


}
