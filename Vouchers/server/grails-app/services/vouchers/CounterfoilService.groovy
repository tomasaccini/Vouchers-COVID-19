package vouchers

import grails.gorm.transactions.Transactional

@Transactional
class CounterfoilService {

    VoucherService voucherService

    Voucher createVoucher(Long id) {
        Counterfoil counterfoil = Counterfoil.get(id)
        if (counterfoil.stock <= 0) {
            throw new RuntimeException("Voucher does not have stock")
        }
        Voucher v = voucherService.createVoucher(counterfoil.voucherInformation)
        counterfoil.addToVouchers(v)
        //TODO: This must be propably an atomic attribute
        counterfoil.stock -= 1
        v
    }
}
