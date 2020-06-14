package vouchers

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ItemSpec  extends Specification implements DomainUnitTest<Item> {

    Product product

    def setup() {
        product = new Product(name: "Wine", description: "red")
        product.save()
    }

    def cleanup() {
        product.delete()
    }

    void "constructor"() {
        Item i = new Item(product: product, quantity: 3)
        i.save()
        expect:"item constructed correctly"
        i != null && i.product.name == "Wine" && i.product.description == "red" && i.quantity == 3
    }

    void "item can't have 0 as quantity"() {
        Item i = new Item(product: product, quantity: 0)
        i.save()
        when:
        i.quantity = 0
        then:
        !i.validate(['quantity'])
        i.errors['quantity'].code == 'min.notmet'
    }

    void "item must have a product"() {
        Item i = new Item(product: null, quantity: 1)
        i.save()
        when:
        i.product = null
        then:
        !i.validate(['product'])
        i.errors['product'].code == 'nullable'
    }

}
