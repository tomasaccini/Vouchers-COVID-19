package vouchers

class Counterfoil {

    String  description
    Double  value
    Date    validFromDate
    Date    validToDate
    //TODO: add state

    static belongsTo = [business: Business]

    static hasMany = [vouchers: Voucher, items: Item]

    static constraints = {
        description     nullable: false, blank: true
        value           nullable: false
        validFromDate   nullable: false
        validToDate     nullable: true
    }


}
