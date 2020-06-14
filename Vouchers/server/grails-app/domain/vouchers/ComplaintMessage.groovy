package vouchers

class ComplaintMessage {

    User owner
    String message
    Date dateCreated = new Date()

    static constraints = {
        owner nullable: false
        message nullable: false
        dateCreated nullable: false
    }
}
