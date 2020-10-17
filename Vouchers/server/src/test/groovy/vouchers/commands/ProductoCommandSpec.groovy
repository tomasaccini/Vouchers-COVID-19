package vouchers.commands

import commands.ProductCommand
import spock.lang.Specification

class ProductoCommandSpec extends Specification {


    def setup() {
    }

    def cleanup() {
    }

    void "constructor"() {
        ProductCommand p = new ProductCommand(name: "Potatoes", description: "chips")
        expect:"product constructed correctly"
        p != null && p.name == "Potatoes" && p.description == "chips"
    }

    void "productCommand can't have empty string as name"() {
        ProductCommand p = new ProductCommand(name: "", description: "chips")
        when:
        p.name = ""
        then:
        !p.validate(['name'])
        p.errors['name'].code == 'blank'
    }

    void "productCommand can't have empty string as description"() {
        ProductCommand p = new ProductCommand(name: "Potatoe", description: "")
        when:
        p.description = ""
        then:
        !p.validate(['description'])
        p.errors['description'].code == 'blank'
    }

    void "productCommand's name can't be null"() {
        ProductCommand p = new ProductCommand(name: null, description: "chips")
        when:
        p.name = null
        then:
        !p.validate(['name'])
        p.errors['name'].code == 'nullable'
    }

    void "productCommand's description can be null"() {
        ProductCommand p = new ProductCommand(name: "Potatoe", description: null)
        when:
        p.description = null
        then:
        p.validate(['description'])
    }
}
