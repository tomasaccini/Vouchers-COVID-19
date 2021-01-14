package vouchers.commands

import commands.ProductoCommand
import spock.lang.Specification

class ProductoCommandSpec extends Specification {


    def setup() {
    }

    def cleanup() {
    }

    void "constructor"() {
        ProductoCommand p = new ProductoCommand(nombre: "Papas", descripcion: "bravas")
        expect: "Producto construido correctamente"
        p != null && p.nombre == "Papas" && p.descripcion == "bravas"
    }

    void "productoCommand no puede tener un string vacio como nombre"() {
        ProductoCommand p = new ProductoCommand(nombre: "", descripcion: "bravas")
        when:
        p.nombre = ""
        then:
        !p.validate(['nombre'])
        p.errors['nombre'].code == 'blank'
    }

    void "productoCommand no puede tener un string vacio como descripcion"() {
        ProductoCommand p = new ProductoCommand(nombre: "Papas", descripcion: "")
        when:
        p.descripcion = ""
        then:
        !p.validate(['descripcion'])
        p.errors['descripcion'].code == 'blank'
    }

    void "productCommand no puede tener el nombre null"() {
        ProductoCommand p = new ProductoCommand(nombre: null, descripcion: "bravas")
        when:
        p.nombre = null
        then:
        !p.validate(['nombre'])
        p.errors['nombre'].code == 'nullable'
    }

    void "productoCommand no puede tener la descripcion null"() {
        ProductoCommand p = new ProductoCommand(nombre: "Papas", descripcion: null)
        when:
        p.descripcion = null
        then:
        p.validate(['descripcion'])
    }
}
