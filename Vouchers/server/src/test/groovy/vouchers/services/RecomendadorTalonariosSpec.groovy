package vouchers.services

import enums.ProductoTipo
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import vouchers.*

@Integration
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RecomendadorTalonariosSpec extends Specification {

    @Autowired
    RecomendadorTalonarios recomendadorTalonarios
    ClienteService clienteService

    private static Boolean setupHecho = false
    static Long clienteId
    static Long negocioId
    static Long itemId
    static InformacionVoucher iv

    def setup() {
        if (setupHecho) return

        Negocio negocio = new Negocio()
        negocio.nombre = "Blue Dog"
        negocio.email = "sales@bluedog.com"
        negocio.contrasenia = "1234"
        negocio.cuentaVerificada = true
        negocio.numeroTelefonico = "1234"
        negocio.categoria = "Cervezería"
        Direccion nuevaDireccion = new Direccion()
        nuevaDireccion.calle = "calle falsa"
        nuevaDireccion.numero = "123"
        nuevaDireccion.departamento = "11D"
        nuevaDireccion.provincia = "Buenos Aires"
        nuevaDireccion.pais = "Argentina"

        negocio.direccion = nuevaDireccion
        negocio.website = "bluedog.com"

        Producto producto = new Producto()
        producto.descripcion = "Hamburguesa con cebolla, cheddar, huevo, jamón, todo."
        producto.nombre = "Hamburguesa Blue Dog"
        producto.tipo = ProductoTipo.COMIDA_RAPIDA
        negocio.addToProductos(producto)
        negocio.save(flush: true, failOnError: true)

        Item item = new Item(producto: producto, cantidad: 1)
        iv = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: new Date('2022/08/15'))
        iv.addToItems(item)

        Cliente cliente = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234")
        cliente.cuentaVerificada = true
        cliente.save(flush: true, failOnError: true)

        setupHecho = true
        clienteId = cliente.id
        negocioId = negocio.id
        itemId = item.id
        Voucher.findAll().each { it.delete(flush:true, failOnError:true) }
        Talonario.findAll().each { it.delete(flush:true, failOnError:true) }
    }

    private Long crearTalonario() {
        Talonario talonario = new Talonario(informacionVoucher: iv, stock: 10, activo: true)
        Negocio negocio = Negocio.findById(negocioId)
        negocio.addToTalonarios(talonario)
        negocio.save(flush: true, failOnError: true)
        return talonario.id
    }

    def cleanup() {
    }

    void "setup test"() {
        expect:
        Negocio.count() == 3
        Producto.count() == 3
        Talonario.count() == 0
    }

    void "recomendador sin talonarios devuelve lista vacia"() {
        List<Talonario> talonarios = recomendadorTalonarios.generarOrdenDeRecomendacion()
        expect: "la lista esta vacia"
        talonarios.size() == 0
    }

    void "recomendador con un talonario sin ventas devuelve ese talonario"() {
        Long talonarioId = crearTalonario();
        List<Talonario> talonarios = recomendadorTalonarios.generarOrdenDeRecomendacion()
        expect: "la lista tiene el unico talonario"
        talonarios.size() == 1
        talonarios[0].id == talonarioId
    }
}
