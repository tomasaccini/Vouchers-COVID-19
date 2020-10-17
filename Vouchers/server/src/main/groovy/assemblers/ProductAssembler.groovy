package assemblers

import commands.ProductCommand
import templates.ConcreteObjectAssembler
import vouchers.Producto

class ProductAssembler extends ConcreteObjectAssembler<Producto, ProductCommand> {

    @Override
    protected Producto getEntity(Long id) {
        return (id == null || id == 0) ? new Producto() : Producto.get(id)
    }

    @Override
    protected ProductCommand createBean() {
        return new ProductCommand()
    }

    @Override
    ProductCommand toBean(Producto domain) {

        ProductCommand bean = super.toBean(domain)

        return bean
    }

    @Override
    Producto fromBean(ProductCommand bean) {

        Producto domain = super.fromBean(bean)

        return domain
    }
}
