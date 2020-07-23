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

    def getByUserId() {
        String userId = params.userId
        List<Voucher> vouchers

        if (userId == null) {
            // TODO maybe throw exception.
            vouchers = voucherService.list()
        } else {
            vouchers = voucherService.listByUserId(userId)
        }

        List<VoucherCommand> voucherCommands = new ArrayList<VoucherCommand>()
        for (def v : vouchers) {
            voucherCommands.add(voucherAssembler.toBean(v))
        }

        respond voucherCommands
    }

    // !!!!
    VoucherCommand create() {
        println "ASDASDsdfs !!!!!"
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

    // !!!!
    def asd() {
        println "!!!! asd2"
        respond [1, 2, 3]
    }
}

// !!!!
class Obj {
    int a
}
