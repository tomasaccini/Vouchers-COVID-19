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

async function cmpVouchers(page, descripcion1, descripcion2) {
  const text = await page.$$eval("div > h2", elements=> elements.map(item=>item.textContent));
  const index1 = text.indexOf(descripcion1);
  const index2 = text.indexOf(descripcion2);
  return index1 < index2;
}

module.exports = {
  voucherEstaVisible,
  comprarVoucher,
  cmpVouchers
};