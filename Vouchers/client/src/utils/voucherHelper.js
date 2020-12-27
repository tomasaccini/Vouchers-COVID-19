
const voucherStates = ['Comprado', 'ConfirmacionPendiente', 'Retirado', 'Expirado']

export default {
  esVoucher: voucher => voucherStates.includes(voucher.state)
}
