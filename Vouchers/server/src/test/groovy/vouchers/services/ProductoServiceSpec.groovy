package vouchers.services

import commands.ProductoCommand
import enums.ProductoTipo
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import vouchers.Direccion
import vouchers.Negocio
import vouchers.Producto
import vouchers.ProductoService

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ProductoServiceSpec extends Specification {

    @Autowired
    ProductoService productoService

    private static Boolean setupHecho = false
    static Long negocioId
    static Long productoId

    def setup() {
        if (setupHecho) return

        Negocio negocio = new Negocio()
        negocio.nombre = "Blue Dog"
        negocio.email = "sales@bluedog.com"
        negocio.contrasenia = "1234"
        negocio.cuentaVerificada = true
        negocio.numeroTelefonico = "1234"
        negocio.categoria = "Cervezería"

        Direccion direccion = new Direccion()
        direccion.calle = "calle falsa"
        direccion.numero = "123"
        direccion.departamento = "11D"
        direccion.provincia = "Buenos Aires"
        direccion.pais = "Argentina"

        negocio.direccion = direccion
        negocio.website = "bluedog.com"
        negocio.save(flush: true, failOnError: true)

        ProductoCommand productoCommand = new ProductoCommand()
        productoCommand.descripcion = "Hamburguesa con cebolla, cheddar, huevo, jamón, todo."
        productoCommand.nombre = "Hamburguesa Blue Dog"
        productoCommand.tipo = ProductoTipo.COMIDA_RAPIDA
        Producto producto = productoService.save(productoCommand, negocio.id)

        setupHecho = true
        negocioId = negocio.id
        productoId = producto.id
    }

    def cleanup() {
    }

    void "hay un producto y un negocio en la base"() {
        expect:
        Producto.count() == 3 && Negocio.count() == 3
    }

    void "save crea un Producto"() {
        given:
        ProductoCommand productoCommand = new ProductoCommand()
        productoCommand.nombre = "Patagonia IPA"
        productoCommand.descripcion = "Cerveza IPA artesanal industrial de la más alta calidad."
        productoCommand.tipo = ProductoTipo.COMIDA_RAPIDA

        when:
        Producto producto2 = productoService.save(productoCommand, negocioId)

        then:
        Producto.count() == 4
        Producto newProduct = Producto.findById(producto2.id)
        newProduct.nombre == productoCommand.nombre
        newProduct.descripcion == productoCommand.descripcion
    }

    def "update modifica correctamente un Producto"() {
        given:
        ProductoCommand productoCommand = new ProductoCommand()
        productoCommand.id = productoId
        productoCommand.nombre = "Hamburguesa PURPLE DOG"
        productoCommand.descripcion = "ahora con mas queso"
        productoCommand.tipo = ProductoTipo.COMIDA_RAPIDA

        when:
        productoService.update(productoCommand)

        then:
        Producto productoModificado = Producto.get(productoId)
        productoModificado.nombre == productoCommand.nombre && productoModificado.descripcion == "ahora con mas queso"
    }

    def "borrar un producto lo elimina del negocio"() {
        when:
        productoService.delete(productoId)

        then:
        Negocio negocio = Negocio.get(negocioId)
        negocio.productos.size() == 0
    }
}
