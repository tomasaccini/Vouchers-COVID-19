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
        counterfoilDB = [
                create("Restaurant 1"),
                create("Restaurant 2"),
                create("Restaurant 3"),
                create("Restaurant 4"),
                create("Restaurant 5"),
        ]
    }

    // !!!!
    Tarifario create(String name) {
        InformacionVoucher vi = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta:  new Date('2020/08/15'))
        Tarifario counterfoil = new Tarifario(informacionVoucher: vi, stock: 5)
        counterfoil.negocio = mockBusiness(name)
        counterfoilDB.add(counterfoil)
        return counterfoil
    }

    // !!!!
    List<Tarifario> getAll() {
        return counterfoilDB
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
        Pais country = new Pais()
        country.name = "Argentina"
        newAddress.pais = country

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

    // !!!!
    Tarifario get(Long counterfoilId) {
        return Tarifario.findById(counterfoilId)
    }

    /*
    * Creates voucher from counterfoil
    * it associates voucher to client
    * decrease the quantity of stock
    */
    Voucher createVoucher(Long counterfoilId, Long clientId) {
        Tarifario counterfoil = Tarifario.get(counterfoilId)
        Cliente client = Cliente.get(clientId)
        if (counterfoil.stock <= 0) {
            throw new RuntimeException("Voucher does not have stock")
        }
        Voucher v = voucherService.createVoucher(counterfoil.informacionVoucher)
        counterfoil.addToVouchers(v)
        client.addToVouchers(v)
        //TODO: This must be propably an atomic attribute
        counterfoil.stock -= 1
        try {
            counterfoil.save(flush:true, failOnError:true)
            client.save(flush:true, failOnError:true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
        return v
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


}
