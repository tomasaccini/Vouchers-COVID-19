async function voucherPuedeIniciarReclamo(page, descripcion) {
  const elemento = await page.$x(`//h2[text()="${descripcion}"]/../..//span[@id="iniciarReclamo"]`);
  return elemento.length > 0;
}

async function iniciarReclamo(page, descripcion, primerMensaje) {
  const iniciarReclamoSelector = `//h2[text()="${descripcion}"]/../..//span[@id="iniciarReclamo"]`;
  const descripcionIniciarReclamoSelector = `//h5[contains(text(), "${descripcion}")]/../..//input[@id="descripcionReclamo"]`;
  const confirmarIniciarReclamoSelector = `//h5[contains(text(), "${descripcion}")]/../../..//button[@id="confirmarInicioReclamo"]`;
  await page.waitForXPath(iniciarReclamoSelector);
  const iniciarReclamoBoton = await page.$x(iniciarReclamoSelector);
  await iniciarReclamoBoton[0].click();
  await page.waitForTimeout(2000);
  await page.waitForXPath(descripcionIniciarReclamoSelector);
  await page.waitForXPath(confirmarIniciarReclamoSelector);
  const descripcionIniciarReclamo = await page.$x(descripcionIniciarReclamoSelector);
  await descripcionIniciarReclamo[0].click();
  await page.keyboard.type(primerMensaje);
  const confirmarIniciarReclamo = await page.$x(confirmarIniciarReclamoSelector);
  await confirmarIniciarReclamo[0].click();
}

async function existeReclamoSobreVoucher(page, descripcion) {
  const elemento = await page.$x(`//div[text()="${descripcion}"]`);
  return elemento.length > 0;
}

module.exports = {
  voucherPuedeIniciarReclamo,
  iniciarReclamo,
  existeReclamoSobreVoucher
};