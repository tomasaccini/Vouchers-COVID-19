package vouchers

import enums.states.ComplaintState

class Complaint {

    String description
    Date dateCreated = new Date()
    Client client
    Business business
    ComplaintState state = ComplaintState.OPENED
    Set messages = []

    static belongsTo = [voucher: Voucher]

    static hasMany = [messages: ComplaintMessage]
    static constraints = {
        description nullable: false
        dateCreated nullable: false
        client nullable: false
        business nullable: false
        state nullable: false
    }

    void addMessage(String message, Client owner) {
        if (client != owner) {
            throw new RuntimeException("Message owner is not the client related to the complaint")
        }
        _addMessage(message, owner)
    }

    void addMessage(String message, Business owner) {
        if (business != owner) {
            throw new RuntimeException("Message owner is not the business related to the complaint")
        }
        _addMessage(message, owner)
    }

    private void _addMessage(String message, User owner){
        this.addToMessages(new ComplaintMessage(owner: owner, message: message))
        state = ComplaintState.ANSWERED
    }

    void close() {
        state = ComplaintState.CLOSED
    }

    void reopen() {
        state = messages.size() == 0 ? ComplaintState.OPENED : ComplaintState.ANSWERED
    }
}
