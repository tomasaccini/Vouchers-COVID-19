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
        println("!!!! " + userId)

        List<Voucher> vouchers

        if (userId == null) {
            vouchers = voucherService.list()
        } else {
            vouchers = voucherService.listByUserId(userId)
        }

        println("!!!! vouchers.size()" + vouchers.size())
        List<VoucherCommand> voucherCommands = new ArrayList<VoucherCommand>()
        for (def v : vouchers) {
            voucherCommands.add(voucherAssembler.toBean(v))
        }

        respond voucherCommands
    }

    // !!!!
    VoucherCommand create() {
        println "ASDASDsdfs"
        Long clientId = request['clientId']
        Long counterfoilId = request['counterfoilId']

        Counterfoil counterfoil = counterfoilService.get(counterfoilId)
        Voucher voucher = clientService.buyVoucher(clientId, counterfoilId)
        VoucherCommand voucherCommand = VoucherAssembler.fromBean(voucher)
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
