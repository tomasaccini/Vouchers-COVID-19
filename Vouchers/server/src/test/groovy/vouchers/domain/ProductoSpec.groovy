package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Producto

class ProductoSpec extends Specification implements DomainUnitTest<Producto> {

    def setup() {
    }

    def cleanup() {
    }

    void "constructor"() {
        Producto p = new Producto(nombre: "Papas", descripcion: "bravas")
        expect:"producto construido correctamente"
        p != null && p.nombre == "Papas" && p.descripcion == "bravas"
    }

    void "el producto no puede tener un string vacio como nombre"() {
        Producto p = new Producto(nombre: "", descripcion: "bravas")
        when:
        p.nombre = ""
        then:
        !p.validate(['nombre'])
        p.errors['nombre'].code == 'blank'
    }

    void "el producto no puede tener un string vacio como descripcion"() {
        Producto p = new Producto(nombre: "Papas", descripcion: "")
        when:
        p.descripcion = ""
        then:
        !p.validate(['descripcion'])
        p.errors['descripcion'].code == 'blank'
    }

    void "el nombre no puede ser null"() {
        Producto p = new Producto(nombre: null, descripcion: "bravas")
        when:
        p.nombre = null
        then:
        !p.validate(['nombre'])
        p.errors['nombre'].code == 'nullable'
    }

    void "la descripcion no puede ser null"() {
        Producto p = new Producto(nombre: "Papas", descripcion: null)
        when:
        p.descripcion = null
        then:
        p.validate(['descripcion'])
    }


}
