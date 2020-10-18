package vouchers


import grails.rest.*
import grails.converters.*

class ReclamoController extends RestfulController{

	static responseFormats = ['json', 'xml']
    ReclamoService reclamoService

    ReclamoController() {
        super(Reclamo)
    }

    /*
    * Returns complaint requested by Id
    * URL/complaints/{id}
    */
    def show(Reclamo reclamo) {
        println("Request for a complaint by id")
        if (!reclamo) {
            response.sendError(404)
        } else {
            respond reclamo
        }
    }

    List<Reclamo> obtenerTodos() {
        println("Request obtener todos los reclamos")
        return reclamoService.obtenerTodos()
    }

    def crearReclamo() {
        println("Request para crear un reclamo")

        Object requestBody = request.JSON
        Long voucherId = requestBody['voucherId']
        String descripcion = requestBody['descripcion']

        try {
            Reclamo reclamo = reclamoService.crearReclamo(voucherId, descripcion)
        } catch (RuntimeException re) {
            response.sendError(400, re.message)
        }
    }

    /*
    * Returns list of businesses's complaints. If specified, it returns a max amount of them.
    * A business complaint is a complaint created by a Client for a voucher
    * URL/complaints/getByBusiness/businessId -> When max is not specified
    * URL/complaints/getByBusiness/businessId?max=n -> When max is specified
    */
    def getPorNegocio(Long negocioId, Integer max) {
        Negocio negocio = Negocio.get(negocioId)
        params.max = Math.min(max ?: 10, 100)
        if (!negocio) {
            // TODO: Mejores mensajes de error / no encontrado
            response.sendError(404)
        }
        println("Request for business complaints, businessId: ${negocio?.id}")
        respond Reclamo.findAllByNegocio(negocio)
    }

    /*
    * Returns list of client's complaints. If specified, it returns a max amount of them.
    * A client complaint is a complaint created for a voucher owned
    * URL/complaints/getByClient/clientId -> When max is not specified
    * URL/complaints/getByClient/clientId?max=n -> When max is specified
    */
    def getPorCliente(Long clienteId, Integer max) {
        Cliente client = Cliente.get(clienteId)
        params.max = Math.min(max ?: 10, 100)
        if (!client) {
            response.sendError(404)
        }
        println("Request for client complaints, clientId: ${client?.id}")
        respond Reclamo.findAllByCliente(client)
    }

    /*
    * Closes complaint given by Id
    * URL/complaints/closeComplaint/{id}
    */
    def cerrarReclamo(Long reclamoId) {
        Reclamo complaint = Reclamo.get(reclamoId)
        if (!complaint) {
            response.sendError(404)
        }
        if (complaint.estaCerrado()) {
            //TODO: Mejorar mensajes
            render (["message":"Complaint already closed", "id": reclamoId] as JSON)
        }
        complaint.cerrar()
        complaint.save()
        render (["id": reclamoId] as JSON)
    }
}
