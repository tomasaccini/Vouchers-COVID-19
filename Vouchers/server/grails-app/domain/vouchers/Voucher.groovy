package vouchers

class Voucher {

    String description
    Date dateCreated
    //TODO: Add state

    static hasMany = [items: Item]

    static belongsTo = [client: Client, counterfoil: Counterfoil]

    static constraints = {
        items           nullable: false
        description     nullable: true, blank: true
        dateCreated     nullable: false
    }
}
