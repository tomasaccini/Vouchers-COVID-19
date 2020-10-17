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
        Producto p = new Producto(nombre: "Potatoes", descripcion: "chips")
        expect:"product constructed correctly"
        p != null && p.nombre == "Potatoes" && p.descripcion == "chips"
    }

    void "product can't have empty string as name"() {
        Producto p = new Producto(nombre: "", descripcion: "chips")
        when:
        p.nombre = ""
        then:
        !p.validate(['name'])
        p.errors['name'].code == 'blank'
    }

    void "product can't have empty string as description"() {
        Producto p = new Producto(nombre: "Potatoe", descripcion: "")
        when:
        p.descripcion = ""
        then:
        !p.validate(['description'])
        p.errors['description'].code == 'blank'
    }

    void "name can't be null"() {
        Producto p = new Producto(nombre: null, descripcion: "chips")
        when:
        p.nombre = null
        then:
        !p.validate(['name'])
        p.errors['name'].code == 'nullable'
    }

    void "description can be null"() {
        Producto p = new Producto(nombre: "Potatoe", descripcion: null)
        when:
        p.descripcion = null
        then:
        p.validate(['description'])
    }


}
