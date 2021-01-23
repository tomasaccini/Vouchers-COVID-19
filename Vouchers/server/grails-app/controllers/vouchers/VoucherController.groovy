package vouchers

import assemblers.VoucherAssembler
import commands.VoucherCommand
import grails.rest.RestfulController

class VoucherController extends RestfulController {

    VoucherAssembler voucherAssembler
    VoucherService voucherService
    ClienteService clienteService

    static responseFormats = ['json', 'xml']

    VoucherController() {
        super(Voucher)
    }

    /*
    * Responds with max number of vouchers requestes
    * If max is not specified, no more than 100 are given.
    * URL/vouchers -> When max is not specified
    * URL/vouchers?max=n -> When max is specified
    */

    def index(Integer max) {
        println("Asking for vouchers list, maz size: ${params.max}")
        params.max = Math.min(max ?: 50, 100)
        respond Voucher.list(params)
    }

    /*
    * Given an user id, it respond with all vouchers owned by the user.
    * Vouchers ya confirmados no son devueltos
    * Max value returned can be specified
    * URL/vouchers/obtenerPorUsuario/userId -> When max is not specified
    * URL/vouchers/obtenerPorUsuario/userId?max=n -> When max is specified
    */

    def obtenerPorUsuario(String userId, Integer max) {
        println("Vouchers requested by user id: ${userId}")
        Cliente cliente = Cliente.get(userId)

        if (!cliente) {
            response.sendError(404)
            return
        }

        params.max = Math.min(max ?: 10, 100)

        List<Voucher> vouchers = Voucher.findAllByCliente(cliente, params)

        // !!!! Un cambio que habia hecho flor para traer por estado. Decidimos traer todos y filtrarlos desde la UI.
        /*
        List<Voucher> vouchers = []

        if (!params.estado){
            vouchers = voucherService.buscarPorUsuarioNoCanjeados(cliente, params)
        } else {
            vouchers = voucherService.buscarPorUsuarioYEstado(cliente, params.estado, params)
        }
         */

        List<VoucherCommand> voucherCommands = []

        for (Voucher voucher : vouchers) {
            voucherCommands.add(voucherAssembler.toBean(voucher))
        }

        respond voucherCommands
    }

    /*
    * Dado el id del cliente y el id del voucher a cambiar
    * URL/vouchers/canjear?clienteId={id}&voucherId={id}
    */

    def solicitarCanje(Long voucherId) {
        println("Request de solicitar canje voucher")

        Object requestBody = request.JSON
        Long clienteId = requestBody['clienteId'].toLong()

        try {
            Voucher voucher = voucherService.solicitarCanje(voucherId, clienteId)
            respond voucherAssembler.toBean(voucher)
        } catch (Exception e) {
            println(e.stackTrace)
            throw e
            // response.sendError(400, e.message)
        }
    }

    /*
    * Dado el id del cliente y el id del voucher a cambiar
    * URL/vouchers/canjear?clienteId={id}&voucherId={id}
    */

    def cancelarSolicitudDeCanje(Long voucherId) {
        println("Request de cancelar solicitud de voucher")

        Object requestBody = request.JSON
        Long clienteId = requestBody['clienteId'].toLong()

        try {
            Voucher voucher = voucherService.cancelarSolicitudDeCanje(voucherId, clienteId)
            respond voucherAssembler.toBean(voucher)
        } catch (Exception e) {
            println(e.stackTrace)
            response.sendError(400, e.message)
        }
    }

    /*
    * Dado el id del cliente y el id del voucher a cambiar
    * URL/vouchers/confirmar?negocioId={id}&voucherId={id}
    */

    def confirmarCanje(Long voucherId) {
        println("Request de confirmar voucher")

        Object requestBody = request.JSON
        Long negocioId = requestBody['negocioId'].toLong()

        try {
            Voucher voucher = voucherService.confirmarCanje(voucherId, negocioId)
            respond voucherAssembler.toBean(voucher)
        } catch (Exception e) {
            println(e.stackTrace)
            response.sendError(400, e.message)
        }
    }

    def obtenerConfirmables(Long negocioId) {
        println("Request de obtener vouchers confirmables")

        try {
            List<Voucher> vouchers = voucherService.obtenerConfirmables(negocioId)

            List<VoucherCommand> voucherCommands = []

            for (Voucher voucher : vouchers) {
                voucherCommands.add(voucherAssembler.toBean(voucher))
            }

            respond voucherCommands
        } catch (Exception e) {
            println(e.stackTrace)
            response.sendError(400, e.message)
        }
    }

    /*
    * Dada una cadena de largo mayor a 2
    * URL/vouchers/search?q={busqueda}
    * URL/vouchers/search?q={busqueda}&max={maximas respuestas deseadas}
    * Devuelve listado de los voucher que poseen esa cadena en su descripcion
    */

    def search(String q, Integer max) {
        def map = [:]
        map.max = Math.min(max ?: 10, 100)
        if (q && q.length() > 2) {
            respond voucherService.findSimilar(q, map)
        } else {
            respond([])
        }
    }

    // !!!! creo que hay que borrar todo esto, no estoy seguro
    VoucherCommand create() {
        println("Voucher creationg requestes")
        println(request.JSON)
        Object requestBody = request.JSON

        Long clienteId
        Long talonarioId
        try {
            clienteId = requestBody['clientId']
            talonarioId = requestBody['counterfoilId']
        } catch (Exception e) {
            return response.sendError(400, "Error en el formato del body del request")
        }

        println('Create a new Voucher with clientId: ' + clienteId + ", counterfoilId: " + talonarioId + ".")

        try {
            Voucher voucher = clienteService.comprarVoucher(clienteId, talonarioId)
            VoucherCommand voucherCommand = voucherAssembler.toBean(voucher)
            respond voucherCommand
        } catch (RuntimeException re) {
            return response.sendError(400, "!!!! asdasdfsa")
        }
    }

    def cambiarRating(){
        println("Rating voucher")
        Object requestBody = request.JSON

        Long voucherId
        Short rating
        try {
            voucherId = requestBody['voucherId']
            rating = requestBody['rating']
        } catch (Exception e) {
            return response.sendError(400, "Error en el formato del body del request")
        }
        try {
            Voucher voucher = voucherService.cambiarRating(voucherId, rating)
            VoucherCommand voucherCommand = voucherAssembler.toBean(voucher)
            respond voucherCommand
        } catch (RuntimeException re) {
            return response.sendError(400, "!!!!")
        }
    }
}
