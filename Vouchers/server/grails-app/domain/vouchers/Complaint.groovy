package vouchers

class Complaint {

    //TODO: Decide whether they must decide the motive or can write what they want
    String  motive
    Date    dateCreated
    //TODO: Add state of complaint

    static belongsTo = [voucher: Voucher]

    static constraints = {
        motive          nullable: false
        dateCreated     nullable: false
    }
}
