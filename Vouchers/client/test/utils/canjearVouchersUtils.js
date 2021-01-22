async function voucherEstaVisible(page, descripcion) {
  const elemento = await page.$x(`//h2[text()="${descripcion}"]`);
  return elemento.length > 0;
}

async function solicitarCanjeVoucher(page, descripcion) {
  const solicitarCanjeSelector = `//h2[text()="${descripcion}"]/../button[span[text()="Canjear"]]`;
  const confirmarSolicitudCanjeSelector = `//h5[contains(text(), "${descripcion}?")]/../../..//button[@id="ConfirmarSolicitudCanjeVoucher"]`;
  await page.waitForXPath(solicitarCanjeSelector);
  const solicitarCanjeBoton = await page.$x(solicitarCanjeSelector);
  await solicitarCanjeBoton[0].click();
  await page.waitForTimeout(2000);
  await page.waitForXPath(confirmarSolicitudCanjeSelector);
  const confirmarSolicitudCanje = await page.$x(confirmarSolicitudCanjeSelector);
  await confirmarSolicitudCanje[0].click();
}

module.exports = {
  voucherEstaVisible,
  solicitarCanjeVoucher
};