package vouchers.domain

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import enums.states.ComplaintState
import vouchers.Address
import vouchers.Business
import vouchers.Client
import vouchers.Complaint
import vouchers.Counterfoil
import vouchers.Country
import vouchers.Item
import vouchers.Product
import vouchers.Voucher
import vouchers.VoucherInformation

class ComplaintSpec extends Specification implements DomainUnitTest<Complaint> {

    def createVoucherInformation(valid_until = new Date('2020/12/31')) {
        Product p1 = new Product(name:"Hamburguesa", description: "Doble cheddar")
        Product p2 = new Product(name:"Pinta cerveza", description: "Cerveza artesanal de la casa")
        Item i1 = new Item(product: p1, quantity: 1)
        Item i2 = new Item(product: p2, quantity: 2)

        VoucherInformation vi = new VoucherInformation(price: 400, description: "Promo verano", validFrom: new Date('2020/08/01'), validUntil: valid_until, items: [i1, i2])
        vi
    }

    def createComplaint(Business b, Client c) {
        VoucherInformation vi = createVoucherInformation()
        Counterfoil counterfoil = new Counterfoil(voucherInformation: vi, stock: 3)
        b.addCounterfoil(counterfoil)
        Voucher v = c.buyVoucher(counterfoil)
        Complaint complaint = new Complaint(client: c, business: b, description: "Description of my problem")
        v.complaint = complaint
        complaint
    }

    void "constructor"() {
        Business b = new Business(name: "Burger", phone_number: "123412341234", address: new Address(street: "Libertador", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "burger@gmail.com", password: "burger1234")
        Client c = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        Complaint complaint = createComplaint(b, c)
        expect:"complaint constructed correctly"
        complaint != null && complaint.state == ComplaintState.OPENED && complaint.business == b && complaint.client == c && complaint.messages.size() == 0
    }

    void "add message from business"() {
        Business b = new Business(name: "Burger", phone_number: "123412341234", address: new Address(street: "Libertador", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "burger@gmail.com", password: "burger1234")
        Client c = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        Complaint complaint = createComplaint(b, c)
        String msg = "First msg from business"
        complaint.addMessage(msg, b)
        expect:"complaint message added correctly"
        complaint != null && complaint.state == ComplaintState.ANSWERED && complaint.business == b && complaint.client == c && complaint.messages.size() == 1 && complaint.messages[0].owner == b && complaint.messages[0].message == msg
    }

    void "add message from client"() {
        Business b = new Business(name: "Burger", phone_number: "123412341234", address: new Address(street: "Libertador", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "burger@gmail.com", password: "burger1234")
        Client c = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        Complaint complaint = createComplaint(b, c)
        String msg = "First msg from client"
        complaint.addMessage(msg, c)
        expect:"complaint message added correctly"
        complaint != null && complaint.state == ComplaintState.ANSWERED && complaint.business == b && complaint.client == c && complaint.messages.size() == 1 && complaint.messages[0].owner == c && complaint.messages[0].message == msg
    }

    void "add two messages"() {
        Business b = new Business(name: "Burger", phone_number: "123412341234", address: new Address(street: "Libertador", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "burger@gmail.com", password: "burger1234")
        Client c = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        Complaint complaint = createComplaint(b, c)
        String msg1 = "First msg from business"
        String msg2 = "Second msg from client"
        complaint.addMessage(msg1, b)
        complaint.addMessage(msg2, c)
        expect:"complaint messages added correctly"
        complaint != null && complaint.state == ComplaintState.ANSWERED && complaint.business == b && complaint.client == c && complaint.messages.size() == 2 && complaint.messages[0].owner == b && complaint.messages[0].message == msg1 && complaint.messages[1].owner == c && complaint.messages[1].message == msg2
    }

    void "add message from business and close"() {
        Business b = new Business(name: "Burger", phone_number: "123412341234", address: new Address(street: "Libertador", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "burger@gmail.com", password: "burger1234")
        Client c = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        Complaint complaint = createComplaint(b, c)
        String msg = "First msg from business"
        complaint.addMessage(msg, b)
        complaint.close()
        expect:"complaint message added correctly and closed"
        complaint != null && complaint.state == ComplaintState.CLOSED && complaint.business == b && complaint.client == c && complaint.messages.size() == 1 && complaint.messages[0].owner == b && complaint.messages[0].message == msg
    }

    void "add message from business, close and reopen"() {
        Business b = new Business(name: "Burger", phone_number: "123412341234", address: new Address(street: "Libertador", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "burger@gmail.com", password: "burger1234")
        Client c = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        Complaint complaint = createComplaint(b, c)
        String msg = "First msg from business"
        complaint.addMessage(msg, b)
        complaint.close()
        complaint.reopen()
        expect:"complaint message added correctly, closed and reopened"
        complaint != null && complaint.state == ComplaintState.ANSWERED && complaint.business == b && complaint.client == c && complaint.messages.size() == 1 && complaint.messages[0].owner == b && complaint.messages[0].message == msg
    }

    void "close and reopen without messages"() {
        Business b = new Business(name: "Burger", phone_number: "123412341234", address: new Address(street: "Libertador", number: "1234", country: new Country(name: "Argentina")), category: "Restaurant", email: "burger@gmail.com", password: "burger1234")
        Client c = new Client(full_name: "Ricardo Fort", email: "ricki@gmail.com", password: "ricki1234")
        Complaint complaint = createComplaint(b, c)
        complaint.close()
        complaint.reopen()
        expect:"complaint message added correctly, closed and reopened"
        complaint != null && complaint.state == ComplaintState.OPENED && complaint.business == b && complaint.client == c && complaint.messages.size() == 0
    }
}
