package vouchers


import grails.rest.*
import grails.converters.*

class ComplaintController extends RestfulController{

	static responseFormats = ['json', 'xml']

    ComplaintController(){
        super(Complaint)
    }

    /*
    * Returns complaint requested by Id
    * URL/complaints/{id}
    */
    def show(Complaint complaint){
        println("Request for a complaint by id")
        if (!complaint){
            response.sendError(404)
        } else {
            respond complaint
        }
    }

    /*
    * Returns list of businesses's complaints. If specified, it returns a max amount of them.
    * A business complaint is a complaint created by a Client for a voucher
    * URL/complaints/getByBusiness/businessId -> When max is not specified
    * URL/complaints/getByBusiness/businessId?max=n -> When max is specified
    */
    def getByBusiness(Long businessId, Integer max){
        Business business = Business.get(businessId)
        params.max = Math.min(max ?: 10, 100)
        if (!business){
            // TODO: Mejores mensajes de error / no encontrado
            response.sendError(404)
        }
        println("Request for business complaints, businessId: ${business?.id}")
        respond Complaint.findAllByBusiness(business)
    }

    /*
    * Returns list of client's complaints. If specified, it returns a max amount of them.
    * A client complaint is a complaint created for a voucher owned
    * URL/complaints/getByClient/clientId -> When max is not specified
    * URL/complaints/getByClient/clientId?max=n -> When max is specified
    */
    def getByClient(Long clientId, Integer max){
        Client client = Client.get(clientId)
        params.max = Math.min(max ?: 10, 100)
        if (!client){
            response.sendError(404)
        }
        println("Request for client complaints, clientId: ${client?.id}")
        respond Complaint.findAllByClient(client)
    }

    /*
    * Closes complaint given by Id
    * URL/complaints/closeComplaint/{id}
    */
    def closeComplaint(Long complaintId){
        Complaint complaint = Complaint.get(complaintId)
        if (!complaint){
            response.sendError(404)
        }
        if (complaint.isClosed()){
            //TODO: Mejorar mensajes
            render (["message":"Complaint already closed", "id": complaintId] as JSON)
        }
        complaint.close()
        complaint.save()
        render (["id": complaintId] as JSON)
    }
}
