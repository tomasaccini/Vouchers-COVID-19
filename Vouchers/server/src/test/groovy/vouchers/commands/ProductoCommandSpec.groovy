package vouchers.commands

import commands.ProductoCommand
import spock.lang.Specification

class ProductoCommandSpec extends Specification {


    def setup() {
    }

    def cleanup() {
    }

    void "constructor"() {
        ProductoCommand p = new ProductoCommand(name: "Potatoes", description: "chips")
        expect:"product constructed correctly"
        p != null && p.name == "Potatoes" && p.description == "chips"
    }

    void "productCommand can't have empty string as name"() {
        ProductoCommand p = new ProductoCommand(name: "", description: "chips")
        when:
        p.name = ""
        then:
        !p.validate(['name'])
        p.errors['name'].code == 'blank'
    }

    void "productCommand can't have empty string as description"() {
        ProductoCommand p = new ProductoCommand(name: "Potatoe", description: "")
        when:
        p.description = ""
        then:
        !p.validate(['description'])
        p.errors['description'].code == 'blank'
    }

    void "productCommand's name can't be null"() {
        ProductoCommand p = new ProductoCommand(name: null, description: "chips")
        when:
        p.name = null
        then:
        !p.validate(['name'])
        p.errors['name'].code == 'nullable'
    }

    void "productCommand's description can be null"() {
        ProductoCommand p = new ProductoCommand(name: "Potatoe", description: null)
        when:
        p.description = null
        then:
        p.validate(['description'])
    }
}
