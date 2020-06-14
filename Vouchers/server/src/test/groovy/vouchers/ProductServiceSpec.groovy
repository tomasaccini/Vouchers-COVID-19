package vouchers

import commands.ProductCommand
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ProductServiceSpec extends Specification {

    @Autowired
    ProductService productService

    def setup() {
        Business business = new Business()
        business.name = "Blue Dog"
        business.email = "sales@bluedog.com"
        business.phone_number = "1234"

        Address newAddress = new Address()
        newAddress.street = "calle falsa"
        newAddress.number = "123"
        newAddress.apartment = "11D"
        newAddress.province = "Buenos Aires"
        Country country = new Country()
        country.name = "Argentina"
        newAddress.country = country

        business.address = newAddress
        business.website = "bluedog.com"

        Product product = new Product()
        product.description = "Hamburguesa con cebolla, cheddar, huevo, jamón, todo."
        product.name = "Hamburguesa Blue Dog"
        business.addToProducts(product)
        business.save(flush: true)
    }

    def cleanup() {
    }

    void "test something"() {
        expect:
        Business.count() == 1
    }

    void "save creates a Product"() {
        given:
        ProductCommand productCommand = new ProductCommand()
        productCommand.name = "Patagonia IPA"
        productCommand.description = "Cerveza IPA artesanal industrial de la más alta calidad."

        when:
        Long businessId = 1
        productService.save(productCommand, businessId)

        then:
        Product.count() == 2
        Product newProduct = Product.findById(2)
        newProduct.name == productCommand.name
        newProduct.description == productCommand.description
    }

    def "update properly modifies the Product"() {
        given:
        ProductCommand productCommand = new ProductCommand()
        productCommand.id = 1
        productCommand.name = "Hamburguesa PURPLE DOG"

        when:
        productService.update(productCommand)

        then:
        Product updatedProduct = Product.get(1)
        updatedProduct.name == productCommand.name
    }

    def "delete removes a Product from a Business"() {
        given:
        ProductCommand productCommand = new ProductCommand()
        productCommand.id = 1

        when:
        Long businessId = 1
        productService.delete(productCommand, businessId)

        then:
        Business business = Business.get(1)
        business.products.size() == 0
        Product.countById(1) == 0
    }
}
