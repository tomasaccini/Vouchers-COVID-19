package vouchers

class Voucher {

    VoucherInformation voucherInformation
    Date dateCreated
    //TODO: Add state

    static hasMany = [items: Item]

    static belongsTo = [client: Client, counterfoil: Counterfoil]

    static constraints = {
        voucherInformation     nullable: false, blank: true
        dateCreated     nullable: false
    }
}
