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

    List<VoucherCommand> getAll() {
        List<Voucher> vouchers = voucherService.list()

        List<VoucherCommand> voucherCommands = new ArrayList<VoucherCommand>()
        for (def v : vouchers) {
            voucherCommands.add(voucherAssembler.toBean(v))
        }

        respond voucherCommands
    }

    /*
    * Given an user id, it respond with all vouchers owned by the user.
    * Max value returned can be specified
    * URL/vouchers/getByUser/userId -> When max is not specified
    * URL/vouchers/getByUser/userId?max=n -> When max is specified
    */
    def getByUser(String userId, Integer max) {
        println("Vouchers requested by user id: ${userId}")

        if (!userId){
            response.sendError(404)
            return
        }

        params.max = Math.min(max ?: 10, 100)
        params.userId = userId
        List<Voucher> vouchers = voucherService.listByUserId(userId)

//        List<VoucherCommand> voucherCommands = new ArrayList<VoucherCommand>()
//        for (def v : vouchers) {
//            voucherCommands.add(voucherAssembler.toBean(v))
//        }
        respond vouchers
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