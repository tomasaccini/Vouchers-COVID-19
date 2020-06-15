package commands

import vouchers.Voucher

class CounterfoilCommand {

    Long id
    Long version
    VoucherInformationCommand voucherInformationCommand
    Integer stock
    List<Voucher> vouchers = [].withLazyDefault { new Voucher() }

    static constraints = {
        importFrom Counterfoil
    }
}
