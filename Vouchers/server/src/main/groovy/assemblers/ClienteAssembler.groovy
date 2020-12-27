package assemblers

import commands.ClienteCommand
import templates.ConcreteObjectAssembler
import vouchers.Cliente

class ClienteAssembler extends ConcreteObjectAssembler<Cliente, ClienteCommand> {

    VoucherAssembler voucherAssembler

    @Override
    protected getEntity(Long id) {
        return (id == null || id == 0) ? new Cliente() : Cliente.get(id)
    }

    @Override
    protected createBean() {
        return new ClienteCommand()
    }

    @Override
    ClienteCommand toBean(Cliente domain) {
        ClienteCommand bean = super.toBean(domain)

        if (domain.vouchers){
            bean.vouchersCommand = voucherAssembler.toBeans(domain.vouchers.asList())
        }

        return bean
    }

    @Override
    Cliente fromBean(ClienteCommand bean) {
        Cliente domain = super.fromBean(bean)

        if (bean.vouchersCommand){
            domain.vouchers = voucherAssembler.fromBeans(bean.vouchersCommand.asList())
        }

        return domain
    }
}
