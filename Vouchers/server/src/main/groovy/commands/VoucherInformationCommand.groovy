package commands

import vouchers.Item
import vouchers.VoucherInformation

class VoucherInformationCommand {

    Long id
    Long version
    Double price
    String description
    Date valid_from
    Date valid_until
    List<Item> items = [].withLazyDefault { new Item() }

    static constraints = {
        importFrom VoucherInformation
    }
}
