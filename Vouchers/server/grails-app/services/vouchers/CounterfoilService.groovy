package vouchers

import enums.ProductType
import grails.gorm.transactions.Transactional
import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException

@Transactional
class CounterfoilService {

    VoucherService voucherService

    // !!!!
    List<Counterfoil> counterfoilDB = []

    // !!!!
    CounterfoilService() {
        counterfoilDB = [
                create("Restaurant 1"),
                create("Restaurant 2"),
                create("Restaurant 3"),
                create("Restaurant 4"),
                create("Restaurant 5"),
        ]
    }

    // !!!!
    Counterfoil create(String name) {
        VoucherInformation vi = new VoucherInformation(price: 400, description: "Promo verano", validFrom: new Date('2020/08/01'), validUntil:  new Date('2020/08/15'))
        Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 5)
        counterfoil.business = mockBusiness(name)
        counterfoilDB.add(counterfoil)
        return counterfoil
    }

    // !!!!
    List<Counterfoil> getAll() {
        return counterfoilDB
    }

    // !!!!
    private Business mockBusiness(name) {
        Business business = new Business()
        business.name = name
        business.email = "sales@bluedog.com"
        business.password = "1234"
        business.phone_number = "1234"
        business.category = "Cervezería"
        Address newAddress = new Address()
        newAddress.street = "calle falsa"
        newAddress.number = "123"
        newAddress.apartment = "11D"
        newAddress.province = "Buenos Aires"
        Country country = new Country()
        country.name = "Argentina"
        newAddress.country = country

        business.address = newAddress
        business.website = "bluedog.com"

        Product product = new Product()
        product.description = "Hamburguesa con cebolla, cheddar, huevo, jamón, todo."
        product.name = "Hamburguesa Blue Dog"
        product.type = ProductType.FAST_FOOD
        business.addToProducts(product)

        Item item = new Item(product: product, quantity: 1)
        VoucherInformation vi = new VoucherInformation(price: 400, description: "Promo verano", validFrom: new Date('2020/08/01'), validUntil:  new Date('2020/08/15'))
        vi.addToItems(item)
        // TODO business counterfoils will be recursive
        // Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 3, isActive: true)
        // business.addToCounterfoils(counterfoil)

        return business
    }

    // !!!!
    Counterfoil get(Long counterfoilId) {
        return Counterfoil.findById(counterfoilId)
    }

    /*
    * Creates voucher from counterfoil
    * it associates voucher to client
    * decrease the quantity of stock
    */
    Voucher createVoucher(Long counterfoilId, Long clientId) {
        Counterfoil counterfoil = Counterfoil.get(counterfoilId)
        Client client = Client.get(clientId)
        if (counterfoil.stock <= 0) {
            throw new RuntimeException("Voucher does not have stock")
        }
        Voucher v = voucherService.createVoucher(counterfoil.voucherInformation)
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

    List<Counterfoil> list(Map args) {
        Counterfoil.list(args)
    }

    /*
    * Activates counterfoil
    * If already active, nothing happens
    */
    def activate(Long id) {
        Counterfoil counterfoil = Counterfoil.get(id)
        if (counterfoil.active) {
            return
        }
        counterfoil.active = true
        counterfoil.save(flush:true)
    }

    /*
    * Deactivates counterfoil
    * If already not active, nothing happens
    */
    def deactivate(Long id) {
        Counterfoil counterfoil = Counterfoil.get(id)
        if (!counterfoil.active) {
            return
        }
        counterfoil.active = false
        counterfoil.save(flush:true)
    }


}
