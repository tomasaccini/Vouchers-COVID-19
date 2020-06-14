package vouchers

class Complaint {

    enum ComplaintState {
        OPENED, ANSWERED, CLOSED
    }

    String description
    Date dateCreated = new Date()
    Client client
    Business business
    ComplaintState state = ComplaintState.OPENED
    ArrayList<ComplaintMessage> messages = new ArrayList<ComplaintMessage>()

    static belongsTo = [voucher: Voucher]

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
        messages.add(new ComplaintMessage(owner: owner, message: message))
        state = ComplaintState.ANSWERED
    }

    void close() {
        state = ComplaintState.CLOSED
    }

    void reopen() {
        state = messages.size() == 0 ? ComplaintState.OPENED : ComplaintState.ANSWERED
    }
}
