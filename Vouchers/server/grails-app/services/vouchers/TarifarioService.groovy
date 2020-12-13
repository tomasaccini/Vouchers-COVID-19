package vouchers

import enums.ProductType
import grails.gorm.transactions.Transactional
import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException

@Transactional
class TarifarioService {

    VoucherService voucherService

    // !!!!
    List<Tarifario> counterfoilDB = []

    // !!!!
    TarifarioService() {
        /*
        counterfoilDB = [
                createMock("Restaurant 1"),
                createMock("Restaurant 2"),
                createMock("Restaurant 3"),
                createMock("Restaurant 4"),
                createMock("Restaurant 5"),
        ]
         */
    }

    // !!!!
    Tarifario createMock(String name) {
        InformacionVoucher vi = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta:  new Date('2020/08/15'))
        Tarifario counterfoil = new Tarifario(informacionVoucher: vi, stock: 5)
        counterfoil.negocio = mockBusiness(name)
        counterfoilDB.add(counterfoil)
        return counterfoil
    }

    Tarifario createMock(String negocioId, Integer stock, Double precio, String descripcion, String validoDesdeStr, String validoHastaStr) {
        Negocio negocio = Negocio.findById(negocioId)
        if (negocio == null) {
            throw new RuntimeException('No se puede crear un tarifario para un voucher invalido')
        }

        if (stock < 0) {
            throw new RuntimeException('No se puede crear un tarifario con stock negativo')
        }
        if (precio < 0) {
            throw new RuntimeException('No se puede crear un tarifario con precio negativo')
        }
        if (descripcion.isEmpty()) {
            throw new RuntimeException('No se puede crear un tarifario con descripcion vacia')
        }
        Date validoDesde = new Date(validoDesdeStr)
        Date validoHasta = new Date(validoHastaStr)

        println("!!!!" + validoDesde)

        InformacionVoucher vi = new InformacionVoucher(precio: precio, descripcion: descripcion, validoDesde: validoDesde, validoHasta:  validoHasta)
        Tarifario tarifario = new Tarifario(informacionVoucher: vi, stock: stock, negocio: negocio)
        tarifario.save()

        tarifario
    }

    List<Tarifario> getAll() {
        return Tarifario.findAll()
    }

    // !!!!
    private Negocio mockBusiness(name) {
        Negocio business = new Negocio()
        business.nombre = name
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
        newAddress.pais = "Argentina"

        business.direccion = newAddress
        business.website = "bluedog.com"

        Producto product = new Producto()
        product.descripcion = "Hamburguesa con cebolla, cheddar, huevo, jamón, todo."
        product.nombre = "Hamburguesa Blue Dog"
        product.type = ProductType.FAST_FOOD
        business.addToProducts(product)

        Item item = new Item(producto: product, cantidad: 1)
        InformacionVoucher vi = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta:  new Date('2020/08/15'))
        vi.addToItems(item)
        // TODO business counterfoils will be recursive
        // Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 3, isActive: true)
        // business.addToCounterfoils(counterfoil)

        return business
    }

    Tarifario get(Long counterfoilId) {
        return Tarifario.findById(counterfoilId)
    }

    List<Tarifario> list(Map args) {
        Tarifario.list(args)
    }

    /*
    * Activates counterfoil
    * If already active, nothing happens
    */
    def activate(Long id) {
        Tarifario counterfoil = Tarifario.get(id)
        if (counterfoil.activo) {
            return
        }
        counterfoil.activo = true
        counterfoil.save(flush:true)
    }

    /*
    * Deactivates counterfoil
    * If already not active, nothing happens
    */
    def deactivate(Long id) {
        Tarifario counterfoil = Tarifario.get(id)
        if (!counterfoil.activo) {
            return
        }
        counterfoil.activo = false
        counterfoil.save(flush:true)
    }

    List<Voucher> findSimilar(String q, Map map) {
        String query = "select distinct(t) from Tarifario as t "
        query += " where lower(t.informacionVoucher.descripcion) like :search "
        Tarifario.executeQuery(query, [search: "%${q}%".toLowerCase()] , map)
    }

}
