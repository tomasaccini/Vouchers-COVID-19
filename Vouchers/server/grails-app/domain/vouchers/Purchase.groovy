package vouchers

class Purchase {

    Long business_id
    Long user_id
    Date purchase_timestamp
    Date pickup_timestamp

    static constraints = {
        business_id blank: false, nullable: false
        user_id blank: false, nullable: false
        purchase_timestamp blank: false, nullable: false
    }
}
