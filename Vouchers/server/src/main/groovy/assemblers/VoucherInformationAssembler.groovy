package assemblers

import commands.VoucherInformationCommand
import vouchers.VoucherInformation

class VoucherInformationAssembler extends BaseAssembler {

    VoucherInformationCommand fromDomain(VoucherInformation domain) {
        VoucherInformationCommand command = new VoucherInformationCommand()
        copyProperties(domain, command)
        return command
    }

    VoucherInformation toDomain(VoucherInformationCommand command) {
        VoucherInformation domain = command.id ? VoucherInformation.get(command.id) : new VoucherInformation()
        copyProperties(command, domain)
        return domain
    }
}
