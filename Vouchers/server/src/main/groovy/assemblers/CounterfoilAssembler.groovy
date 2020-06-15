package assemblers

import commands.CounterfoilCommand
import vouchers.Counterfoil

class CounterfoilAssembler extends BaseAssembler {

    VoucherInformationAssembler voucherInformationAssembler

    CounterfoilCommand fromDomain(Counterfoil domain) {
        CounterfoilCommand command = new CounterfoilCommand()
        copyProperties(domain, command)
        command.voucherInformationCommand = voucherInformationAssembler.fromDomain(domain.voucherInformation)
        return command
    }

    Counterfoil toDomain(CounterfoilCommand command) {
        Counterfoil domain = command.id ? Counterfoil.get(command.id) : new Counterfoil()
        copyProperties(command, domain)
        return domain
    }
}
