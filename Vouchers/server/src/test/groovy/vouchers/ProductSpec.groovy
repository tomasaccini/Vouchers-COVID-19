package vouchers

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ProductSpec extends Specification implements DomainUnitTest<Product> {

    def setup() {
    }

    def cleanup() {
    }

    void "constructor"() {
        Product p = new Product(name: "Potatoes", description: "chips")
        expect:"product constructed correctly"
        p != null && p.name == "Potatoes" && p.description == "chips"
    }

    void "product can't have empty string as name"() {
        Product p = new Product(name: "", description: "chips")
        when:
        p.name = ""
        then:
        !p.validate(['name'])
        p.errors['name'].code == 'blank'
    }

    void "product can't have empty string as description"() {
        Product p = new Product(name: "Potatoe", description: "")
        when:
        p.description = ""
        then:
        !p.validate(['description'])
        p.errors['description'].code == 'blank'
    }

    void "name can't be null"() {
        Product p = new Product(name: null, description: "chips")
        when:
        p.name = null
        then:
        !p.validate(['name'])
        p.errors['name'].code == 'nullable'
    }

    void "description can be null"() {
        Product p = new Product(name: "Potatoe", description: null)
        when:
        p.description = null
        then:
        p.validate(['description'])
    }


}
