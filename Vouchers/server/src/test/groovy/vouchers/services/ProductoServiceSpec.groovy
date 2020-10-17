package vouchers.services

import commands.ProductCommand
import enums.ProductType
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import vouchers.Direccion
import vouchers.Negocio
import vouchers.Pais
import vouchers.Producto
import vouchers.ProductoService

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ProductoServiceSpec extends Specification {

    @Autowired
    ProductoService productService

    private static Boolean setupIsDone = false

    def setup() {
        if (setupIsDone) return

        Negocio business = new Negocio()
        business.nombre = "Blue Dog"
        business.email = "sales@bluedog.com"
        business.contrasenia = "1234"
        business.cuentaVerificada = true
        business.numeroTelefonico = "1234"
        business.categoria = "Cervezería"

        Direccion newAddress = new Direccion()
        newAddress.calle = "calle falsa"
        newAddress.numero = "123"
        newAddress.departamento = "11D"
        newAddress.provincia = "Buenos Aires"
        Pais country = new Pais()
        country.name = "Argentina"
        newAddress.pais = country

        business.direccion = newAddress
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
        Producto.count() == 1 && Negocio.count() == 1
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
        Producto.count() == 2
        Producto newProduct = Producto.findById(2)
        newProduct.nombre == productCommand.name
        newProduct.descripcion == productCommand.description
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
        Producto updatedProduct = Producto.get(1)
        updatedProduct.nombre == productCommand.name && updatedProduct.descripcion == "ahora con mas queso"
    }

    def "deleting a product removes it from the Business"() {
        given:
        Long productId = 1

        when:
        productService.delete(productId)

        then:
        Negocio business = Negocio.get(1)
        business.products.size() == 0
    }
}
