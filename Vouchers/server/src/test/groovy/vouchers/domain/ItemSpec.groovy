package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Item
import vouchers.Producto

class ItemSpec  extends Specification implements DomainUnitTest<Item> {

    Producto producto

    def setup() {
        producto = new Producto(nombre: "Vino", descripcion: "rojo")
        producto.save()
    }

    def cleanup() {
        producto.delete()
    }

    void "constructor"() {
        Item i = new Item(producto: producto, cantidad: 3)
        i.save()
        expect:"item construido correctamente"
        i != null && i.producto.nombre == "Vino" && i.producto.descripcion == "rojo" && i.cantidad == 3
    }

    void "item no puede tener cantidad 0"() {
        Item i = new Item(producto: producto, cantidad: 0)
        i.save()
        when:
        i.cantidad = 0
        then:
        !i.validate(['cantidad'])
        i.errors['cantidad'].code == 'min.notmet'
    }

    void "item tiene que tener un producto"() {
        Item i = new Item(producto: null, cantidad: 1)
        i.save()
        when:
        i.producto = null
        then:
        !i.validate(['producto'])
        i.errors['producto'].code == 'nullable'
    }

}
