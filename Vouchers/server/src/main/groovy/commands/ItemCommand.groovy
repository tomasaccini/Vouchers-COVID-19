package commands

import vouchers.Item

class ItemCommand {

    Long id
    Long version
    ProductCommand productCommand
    Integer quantity

    static constraints = {
        importFrom Item
    }

}
