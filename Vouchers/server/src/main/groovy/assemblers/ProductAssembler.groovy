package assemblers

import commands.ProductCommand
import vouchers.Product

class ProductAssembler extends BaseAssembler {

    ProductCommand fromDomain(Product domain) {
        ProductCommand command = new ProductCommand()
        copyProperties(domain, command)
        return command
    }

    Product toDomain(ProductCommand command) {
        Product domain = command.id ? Product.get(command.id) : new Product()
        copyProperties(command, domain)
        return domain
    }
}
