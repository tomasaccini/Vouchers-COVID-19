package vouchers

import assemblers.VoucherAssembler
import commands.VoucherCommand
import grails.rest.*
import grails.converters.*

import static org.springframework.http.HttpStatus.CREATED

class VoucherController extends RestfulController {

    VoucherAssembler voucherAssembler
    VoucherService voucherService
    CounterfoilService counterfoilService
    ClientService clientService

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
        Long clientId = requestBody['clientId']
        Long counterfoilId = requestBody['counterfoilId']

        println('Create a new Voucher with clientId: ' + clientId + ", counterfoilId: " + counterfoilId + ".")

        if (clientId == null || counterfoilId == null) {
            // TODO respond 400 instead of throwing exception !!!!
            throw new IllegalArgumentException("Both 'clientId' and 'counterfoilId' are needed")
        }

        // TODO remove this mock. Now is just getting a random mocked voucher !!!!
        // ------------------
        Voucher v = voucherService.list()[0]
        return respond(voucherAssembler.toBean(v))
        // ------------------

        Counterfoil counterfoil = counterfoilService.get(counterfoilId)
        Voucher voucher = clientService.buyVoucher(clientId, counterfoil)
        VoucherCommand voucherCommand = voucherAssembler.toBean(voucher)
        respond voucherCommand
    }

}