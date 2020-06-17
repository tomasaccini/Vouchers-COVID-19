package vouchers

import grails.gorm.transactions.Transactional

@Transactional
class CounterfoilService {

    VoucherService voucherService

   Voucher createVoucher(Long id){
       Counterfoil counterfoil = Counterfoil.get(id)
       Voucher voucher = voucherService.createVoucher(counterfoil.voucherInformation)
       return voucher
   }
}
