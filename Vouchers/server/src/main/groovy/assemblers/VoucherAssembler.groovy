package assemblers

import commands.VoucherCommand
import vouchers.Voucher

class VoucherAssembler extends BaseAssembler{

    VoucherCommand fromDomain(Voucher domain) {
        VoucherCommand command = new VoucherCommand()
        copyProperties(domain, command)
        return command
    }

    Voucher toDomain(VoucherCommand command) {
        Voucher domain = command.id ? Voucher.get(command.id) : new Voucher()
        copyProperties(command, domain)
        return domain
    }

}
