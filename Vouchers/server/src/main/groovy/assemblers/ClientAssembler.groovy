package assemblers

import commands.ClientCommand
import templates.ConcreteObjectAssembler
import vouchers.Client

class ClientAssembler extends ConcreteObjectAssembler<Client, ClientCommand>{

    VoucherAssembler voucherAssembler

    @Override
    protected getEntity(Long id) {
        return (id == null || id == 0) ? new Client() : Client.get(id)
    }

    @Override
    protected createBean() {
        return new ClientCommand()
    }

    @Override
    ClientCommand toBean(Client domain) {
        ClientCommand bean = super.toBean(domain)

        if (domain.vouchers){
            bean.vouchersCommand = voucherAssembler.toBeans(domain.vouchers.asList())
        }

        return bean
    }

    @Override
    Client fromBean(ClientCommand bean) {
        Client domain = super.fromBean(bean)

        if (bean.vouchersCommand){
            domain.vouchers = voucherAssembler.fromBeans(bean.vouchersCommand.asList())
        }

        return domain
    }
}
