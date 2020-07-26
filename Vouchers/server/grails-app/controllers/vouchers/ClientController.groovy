package vouchers

import grails.rest.*

import javax.transaction.Transactional

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

    @Transactional
    def save(Client client){
        println("Creating client")
        if (!client || client.hasErrors()){
            respond client.errors
            return
        }
        try {
            client.save(failOnError: true, flush:true)
            respond client
        } catch (Exception e){
            response.sendError(404)
        }

    }

    @Transactional
    def update(Client client){
        println("Updating client")
        if (client?.hasErrors()){
            respond client.errors
            return
        }
        //TODO: Check version for update crash
        try {
            client.save(failOnError: true, flush:true)
            respond client
        } catch (Exception e){
            response.sendError(404)
        }
    }

}
