package assemblers

import commands.ClientCommand
import templates.ConcreteObjectAssembler
import vouchers.Cliente

class ClientAssembler extends ConcreteObjectAssembler<Cliente, ClientCommand>{

    VoucherAssembler voucherAssembler

    @Override
    protected getEntity(Long id) {
        return (id == null || id == 0) ? new Cliente() : Cliente.get(id)
    }

    @Override
    protected createBean() {
        return new ClientCommand()
    }

    @Override
    ClientCommand toBean(Cliente domain) {
        ClientCommand bean = super.toBean(domain)

        if (domain.vouchers){
            bean.vouchersCommand = voucherAssembler.toBeans(domain.vouchers.asList())
        }

        return bean
    }

    @Override
    Cliente fromBean(ClientCommand bean) {
        Cliente domain = super.fromBean(bean)

        if (bean.vouchersCommand){
            domain.vouchers = voucherAssembler.fromBeans(bean.vouchersCommand.asList())
        }

        return domain
    }
}
