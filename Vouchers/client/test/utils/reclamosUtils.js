async function voucherPuedeIniciarReclamo(page, descripcion) {
  const elemento = await page.$x(`//h2[text()="${descripcion}"]/../..//span[@id="iniciarReclamo"]`);
  return elemento.length > 0;
}

module.exports = {
  voucherPuedeIniciarReclamo
};