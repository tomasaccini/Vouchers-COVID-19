package assemblers

import commands.ItemCommand
import templates.ConcreteObjectAssembler
import vouchers.Item

class ItemAssembler extends ConcreteObjectAssembler<Item, ItemCommand> {

    ProductAssembler productAssembler

    @Override
    protected Item getEntity(Long id) {
        return (id == null || id == 0) ? new Item() : Item.get(id)
    }

    @Override
    protected ItemCommand createBean() {
        return new ItemCommand()
    }

    @Override
    ItemCommand toBean(Item domain) {

        ItemCommand bean = super.toBean(domain)

        if(domain.product) {
            bean.productCommand = productAssembler.toBean(domain.product)
        }

        return bean
    }

    @Override
    Item fromBean(ItemCommand bean) {

        Item domain = super.fromBean(bean)
        domain.product  = productAssembler.fromBean(bean.productCommand)

        return domain
    }
}
