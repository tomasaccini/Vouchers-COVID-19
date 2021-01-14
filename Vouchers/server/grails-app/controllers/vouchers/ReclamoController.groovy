package vouchers

import assemblers.ReclamoAssembler
import commands.ReclamoCommand
import grails.rest.RestfulController

class ReclamoController extends RestfulController{

	static responseFormats = ['json', 'xml']
    ReclamoService reclamoService
    ReclamoAssembler reclamoAssembler = new ReclamoAssembler()

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
        respond reclamoService.obtenerTodos()
    }

    ReclamoCommand obtenerReclamo(Long reclamoId) {
        println("Request obtener el reclamo con id: ${reclamoId}")
        try {
            Reclamo reclamo = reclamoService.obtener(reclamoId)
            ReclamoCommand reclamoCommand = reclamoAssembler.toBean(reclamo)
            respond reclamoCommand
        } catch (RuntimeException re) {
            response.sendError(404, re.message)
        }
    }

    def abrirReclamo() {
        println("Request para crear un reclamo")

        Object requestBody = request.JSON
        Long voucherId = requestBody['voucherId']
        String descripcion = requestBody['descripcion']

        try {
            Reclamo reclamo = reclamoService.abrirReclamo(voucherId, descripcion)
            respond reclamoAssembler.toBean(reclamo)
        } catch (RuntimeException re) {
            response.sendError(400, re.message)
        }
    }

    def nuevoMensaje(Long reclamoId) {
        println("Request para agregar un mensaje a un reclamo")

        Object requestBody = request.JSON
        Long usuarioId = requestBody['usuarioId']
        String mensaje = requestBody['mensaje']

        try {
            Reclamo reclamo = reclamoService.nuevoMensaje(reclamoId, usuarioId, mensaje)
            respond reclamoAssembler.toBean(reclamo)
        } catch (RuntimeException re) {
            response.sendError(400, re.message)
        }
    }

    def obtenerPorUsuario(Long usuarioId) {
        List<Reclamo> reclamos = reclamoService.obtenerPorUsuario(usuarioId)

        println("!!!! obtenerPorUsuario")
        println(reclamos.size())

        List<ReclamoCommand> reclamosCommand = []

        for (def reclamo : reclamos) {
            reclamosCommand.add(reclamoAssembler.toBean(reclamo))
        }

        respond reclamosCommand
    }

    /*
    * Returns list of businesses's complaints. If specified, it returns a max amount of them.
    * A business complaint is a complaint created by a Client for a voucher
    * URL/complaints/getByBusiness/businessId -> When max is not specified
    * URL/complaints/getByBusiness/businessId?max=n -> When max is specified
    */
    def obtenerPorNegocio(Long negocioId, Integer max) {
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
    def obtenerPorCliente(Long clienteId, Integer max) {
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
    * post - URL/reclamos/{id}/cerrar
    */
    def cerrarReclamo(Long reclamoId) {
        println("Request para cerrar un reclamo")

        Object requestBody = request.JSON
        String usuarioIdStr = requestBody['usuarioId']
        Long usuarioId = usuarioIdStr.toLong()

        try {
            Reclamo reclamo = reclamoService.cerrarReclamo(reclamoId, usuarioId)
            ReclamoCommand reclamoCommand = reclamoAssembler.toBean(reclamo)

            respond obtenerPorUsuario(usuarioId)
        } catch (RuntimeException re) {
            response.sendError(400, re.message)
        }
    }
}
