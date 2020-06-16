package vouchers.services

import commands.VoucherCommand
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification
import vouchers.Address
import vouchers.Business
import vouchers.Country
import vouchers.Product
import vouchers.Voucher
import vouchers.VoucherService

@Integration
@Rollback
class VoucherServiceSpec extends Specification{

    @Autowired
    VoucherService voucherService

    private static Boolean setupIsDone = false

    def setup() {
        if (setupIsDone) return

        Business business = new Business()
        business.name = "Blue Dog"
        business.email = "sales@bluedog.com"
        business.password = "1234"
        business.verified_account = true
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
        business.addToProducts(product)
        business.save(flush: true, failOnError: true)
        setupIsDone = true
    }

    void "save creates a Voucher"() {
        given:
        VoucherCommand voucherCommand = new VoucherCommand()

        when:
        voucherService.save(voucherCommand)

        then:
        Voucher.count() == 1
    }

}
