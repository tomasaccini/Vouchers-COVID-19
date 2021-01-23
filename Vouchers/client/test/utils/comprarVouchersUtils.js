async function voucherEstaVisible(page, descripcion) {
  const elemento = await page.$x(`//h2[text()="${descripcion}"]`);
  return elemento.length > 0;
}

async function comprarVoucher(page, descripcion) {
  const comprarBotonSelector = `//h2[text()="${descripcion}"]/../button[span[text()="Comprar"]]`;
  const confirmarCompraSelector = `//h5[contains(text(), "${descripcion}?")]/../../..//button[@id="ConfirmarCompraVoucher"]`;
  await page.waitForXPath(comprarBotonSelector);
  const comprarBoton = await page.$x(comprarBotonSelector);
  await comprarBoton[0].click();
  await page.waitForTimeout(2000);
  await page.waitForXPath(confirmarCompraSelector);
  const confirmarCompra = await page.$x(confirmarCompraSelector);
  await confirmarCompra[0].click();
}

module.exports = {
  voucherEstaVisible,
  comprarVoucher
};