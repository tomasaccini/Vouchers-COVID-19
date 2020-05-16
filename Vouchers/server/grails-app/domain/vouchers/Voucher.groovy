package vouchers

class Voucher {

    Long business_id
    String description
    String category
    Date start_sell_date
    Date finish_sell_date
    Date start_retire_date
    Date finish_retire_date
    Boolean published
    Boolean active
    Long max_sells

    static constraints = {
        business_id blank: false, nullable: false
        description blank: false, nullable: false
        category blank: false, nullable: false
    }
}
