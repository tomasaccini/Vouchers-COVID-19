package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Item
import vouchers.Producto

class ItemSpec  extends Specification implements DomainUnitTest<Item> {

    Producto product

    def setup() {
        product = new Producto(nombre: "Wine", descripcion: "red")
        product.save()
    }

    def cleanup() {
        product.delete()
    }

    void "constructor"() {
        Item i = new Item(producto: product, cantidad: 3)
        i.save()
        expect:"item constructed correctly"
        i != null && i.producto.nombre == "Wine" && i.producto.descripcion == "red" && i.cantidad == 3
    }

    void "item can't have 0 as quantity"() {
        Item i = new Item(producto: product, cantidad: 0)
        i.save()
        when:
        i.cantidad = 0
        then:
        !i.validate(['quantity'])
        i.errors['quantity'].code == 'min.notmet'
    }

    void "item must have a product"() {
        Item i = new Item(producto: null, cantidad: 1)
        i.save()
        when:
        i.producto = null
        then:
        !i.validate(['product'])
        i.errors['product'].code == 'nullable'
    }

}
