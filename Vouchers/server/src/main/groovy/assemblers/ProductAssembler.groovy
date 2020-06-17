package assemblers

import commands.ProductCommand
import templates.ConcreteObjectAssembler
import vouchers.Product

class ProductAssembler extends ConcreteObjectAssembler<Product, ProductCommand> {

    @Override
    protected Product getEntity(Long id) {
        return (id == null || id == 0) ? new Product() : Product.get(id)
    }

    @Override
    protected ProductCommand createBean() {
        return new ProductCommand()
    }

    @Override
    ProductCommand toBean(Product domain) {

        ProductCommand bean = super.toBean(domain)

        return bean
    }

    @Override
    Product fromBean(ProductCommand bean) {

        Product domain = super.fromBean(bean)

        return domain
    }
}
