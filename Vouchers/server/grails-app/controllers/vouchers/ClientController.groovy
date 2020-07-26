package vouchers

import grails.rest.*

class ClientController extends RestfulController{

	static responseFormats = ['json', 'xml']
	
    ClientController(){
        super(Client)
    }

    /*
    * Returns client requested by Id
    * URL/clients/{id}
    */
    def show(Client client){
        println("Request for a client by id")
        if (!client){
            response.sendError(404)
        } else {
            respond client
        }
    }

}
