
const estadosVoucher = ['Comprado', 'ConfirmacionPendiente', 'Canjeado', 'Expirado']

export default {
  esVoucher: voucher => estadosVoucher.includes(voucher.estado)
}
