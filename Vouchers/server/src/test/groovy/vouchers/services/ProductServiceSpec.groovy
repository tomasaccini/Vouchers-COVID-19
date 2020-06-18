package vouchers.services

import commands.ProductCommand
import enums.ProductType
import grails.gorm.transactions.Rollback
import grails.gorm.transactions.Transactional
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import vouchers.Address
import vouchers.Business
import vouchers.Country
import vouchers.Product
import vouchers.ProductService

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ProductServiceSpec extends Specification {

    @Autowired
    ProductService productService

    private static Boolean setupIsDone = false

    def setup() {
        if (setupIsDone) return

        Business business = new Business()
        business.name = "Blue Dog"
        business.email = "sales@bluedog.com"
        business.password = "1234"
        business.verifiedAccount = true
        business.phone_number = "1234"
        business.category = "Cervezería"

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
        business.save(flush: true, failOnError: true)

        ProductCommand productCommand = new ProductCommand()
        productCommand.description = "Hamburguesa con cebolla, cheddar, huevo, jamón, todo."
        productCommand.name = "Hamburguesa Blue Dog"
        productCommand.type = ProductType.FAST_FOOD
        productService.save(productCommand, 1)

        setupIsDone = true
    }

    def cleanup() {
    }

    void "there is one product and one business created in database"() {
        expect:
        Product.count() == 1 && Business.count() == 1
    }

    void "save creates a Product"() {
        given:
        ProductCommand productCommand = new ProductCommand()
        productCommand.name = "Patagonia IPA"
        productCommand.description = "Cerveza IPA artesanal industrial de la más alta calidad."
        productCommand.type = ProductType.FAST_FOOD

        when:
        productService.save(productCommand, 1)

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
        productCommand.description = "ahora con mas queso"
        productCommand.type = ProductType.FAST_FOOD

        when:
        productService.update(productCommand)

        then:
        Product updatedProduct = Product.get(1)
        updatedProduct.name == productCommand.name && updatedProduct.description == "ahora con mas queso"
    }

    def "deleting a product removes it from the Business"() {
        given:
        Long productId = 1

        when:
        productService.delete(productId)

        then:
        Business business = Business.get(1)
        business.products.size() == 0
    }
}
