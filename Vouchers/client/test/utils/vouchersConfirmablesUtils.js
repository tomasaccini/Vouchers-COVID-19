async function voucherEstaPendienteDeConfirmacion(page, descripcion) {
  const elemento = await page.$x(`//h2[text()="${descripcion}"]/../button[span[text()="Confirmar"]]`);
  return elemento.length > 0;
}

async function confirmarCanjeVoucher(page, descripcion) {
  const confirmarCanjeSelector = `//h2[text()="${descripcion}"]/../button[span[text()="Confirmar"]]`;
  const confirmarCanjeConfirmarSelector = `//h5[contains(text(), "${descripcion}?")]/../../..//button[@id="ConfirmarCanjeVoucher"]`;
  await page.waitForXPath(confirmarCanjeSelector);
  const confirmarCanjeBoton = await page.$x(confirmarCanjeSelector);
  await confirmarCanjeBoton[0].click();
  await page.waitForTimeout(2000);
  await page.waitForXPath(confirmarCanjeConfirmarSelector);
  const confirmarCanje = await page.$x(confirmarCanjeConfirmarSelector);
  await confirmarCanje[0].click();
}

module.exports = {
  confirmarCanjeVoucher,
  voucherEstaPendienteDeConfirmacion
};