
const voucherStates = ['Comprado', 'ConfirmacionPendiente', 'Canjeado', 'Expirado']

export default {
  esVoucher: voucher => voucherStates.includes(voucher.state)
}
