package assemblers

import commands.ProductoCommand
import templates.ConcreteObjectAssembler
import vouchers.Producto

class ProductAssembler extends ConcreteObjectAssembler<Producto, ProductoCommand> {

    @Override
    protected Producto getEntity(Long id) {
        return (id == null || id == 0) ? new Producto() : Producto.get(id)
    }

    @Override
    protected ProductoCommand createBean() {
        return new ProductoCommand()
    }

    @Override
    ProductoCommand toBean(Producto domain) {

        ProductoCommand bean = super.toBean(domain)

        return bean
    }

    @Override
    Producto fromBean(ProductoCommand bean) {

        Producto domain = super.fromBean(bean)

        return domain
    }
}
