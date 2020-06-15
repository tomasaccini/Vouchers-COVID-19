package assemblers

import commands.ItemCommand
import vouchers.Item

class ItemAssembler extends BaseAssembler {

    ProductAssembler productAssembler

    ItemCommand fromDomain(Item domain) {
        ItemCommand command = new ItemCommand()
        copyProperties(domain, command)
        command.productCommand = productAssembler.fromDomain(domain.product)
        return command
    }

    Item toDomain(ItemCommand command) {
        Item domain = command.id ? Item.get(command.id) : new Item()
        copyProperties(command, domain)
        domain.product = productAssembler.toDomain(command.productCommand)
        return domain
    }
}
