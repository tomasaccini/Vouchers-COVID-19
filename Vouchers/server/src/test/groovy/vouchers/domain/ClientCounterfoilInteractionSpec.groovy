package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import vouchers.Client
import vouchers.ClientCounterfoilInteraction
import vouchers.Counterfoil
import vouchers.Item
import vouchers.Product
import vouchers.VoucherInformation

class ClientCounterfoilInteractionSpec extends Specification implements DomainUnitTest<ClientCounterfoilInteraction> {

    def createVoucherInformation(valid_until = new Date('2020/12/31')) {
        Product p1 = new Product(name:"Hamburguesa", description: "Doble cheddar")
        Product p2 = new Product(name:"Pinta cerveza", description: "Cerveza artesanal de la casa")
        Item i1 = new Item(product: p1, quantity: 1)
        Item i2 = new Item(product: p2, quantity: 2)

        VoucherInformation vi = new VoucherInformation(price: 400, description: "Promo verano", validFrom: new Date('2020/08/01'), validUntil: valid_until, items: [i1, i2])
        vi
    }

    def cleanup() {
    }

    void "constructor view"() {
        Client c = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        VoucherInformation vi = createVoucherInformation()
        Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 3)
        ClientCounterfoilInteraction cci = new ClientCounterfoilInteraction(client: c, counterfoil: counterfoil, interaction: ClientCounterfoilInteraction.Interactions.VIEW)
        expect:"interaction constructed correctly"
        cci != null && cci.client == c && cci.counterfoil == counterfoil && cci.dateCreated != null && cci.interaction == ClientCounterfoilInteraction.Interactions.VIEW
    }

    void "constructor buy"() {
        Client c = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        VoucherInformation vi = createVoucherInformation()
        Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 3)
        ClientCounterfoilInteraction cci = new ClientCounterfoilInteraction(client: c, counterfoil: counterfoil, interaction: ClientCounterfoilInteraction.Interactions.BUY)
        expect:"interaction constructed correctly"
        cci != null && cci.client == c && cci.counterfoil == counterfoil && cci.dateCreated != null && cci.interaction == ClientCounterfoilInteraction.Interactions.BUY
    }
}
