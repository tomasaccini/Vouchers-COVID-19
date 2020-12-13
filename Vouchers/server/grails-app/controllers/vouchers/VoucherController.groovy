package vouchers

import assemblers.VoucherAssembler
import commands.VoucherCommand
import grails.rest.*

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
    def index(Integer max){
        println("Asking for vouchers list, maz size: ${params.max}")
        params.max = Math.min(max ?: 10, 100)
        respond Voucher.list(params)
    }

    /*
    * Given an user id, it respond with all vouchers owned by the user.
    * Max value returned can be specified
    * URL/vouchers/getByUser/userId -> When max is not specified
    * URL/vouchers/getByUser/userId?max=n -> When max is specified
    */
    def getByUser(String userId, Integer max) {
        println("Vouchers requested by user id: ${userId}")
        Cliente cliente = Cliente.get(userId)

        if (!cliente){
            response.sendError(404)
            return
        }

        params.max = Math.min(max ?: 10, 100)
        respond Voucher.findAllByCliente(cliente, params)
    }

    /*
    * Dado el id del cliente y el id del voucher a cambiar
    * URL/vouchers/canjear?clienteId={id}&voucherId={id}
    */
    def canjear(Long clienteId, Long voucherId) {
        Cliente cliente = Cliente.get(clienteId)
        if (!cliente || !cliente.getVoucher(voucherId)){
            respond 404
            return
        }
        voucherService.retire(voucherId)
        respond Voucher.get(voucherId)
    }

    /*
    * Dado el id del cliente y el id del voucher a cambiar
    * URL/vouchers/confirmar?negocioId={id}&voucherId={id}
    */
    def confirmar(Long negocioId, Long voucherId) {
        Negocio negocio = Negocio.get(negocioId)
        Voucher voucher = Voucher.get(voucherId)
        if (!negocio || !voucher.perteneceAlNegocio(negocioId)){
            respond 404
            return
        }
        voucherService.confirm(voucherId)
        respond Voucher.get(voucherId)
    }

    /*
    * Dada una cadena de largo mayor a 2
    * URL/vouchers/search?q={busqueda}
    * URL/vouchers/search?q={busqueda}&max={maximas respuestas deseadas}
    * Devuelve listado de los voucher que poseen esa cadena en su descripcion
    */
    def search(String q, Integer max){
        def map = [:]
        map.max = Math.min( max ?: 10, 100)
        if (q &&  q.length() > 2){
            respond voucherService.findSimilar(q, map)
        } else {
            respond([])
        }
    }

    // !!!!
    VoucherCommand create() {
        println("Voucher creationg requestes")
        println(request.JSON)
        Object requestBody = request.JSON

        Long clienteId
        Long tarifarioId
        try {
            clienteId = requestBody['clientId']
            tarifarioId = requestBody['counterfoilId']
        } catch (Exception e) {
            return response.sendError(400, "Error en el formato del body del request")
        }

        println('Create a new Voucher with clientId: ' + clienteId + ", counterfoilId: " + tarifarioId + ".")

        try {
            Voucher voucher = clienteService.comprarVoucher(clienteId, tarifarioId)
            VoucherCommand voucherCommand = voucherAssembler.toBean(voucher)
            respond voucherCommand
        } catch (RuntimeException re) {
            return response.sendError(400, "!!!! asdasdfsa")
        }
    }
}