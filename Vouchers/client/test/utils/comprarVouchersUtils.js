async function voucherEstaVisible(page, descripcion) {
  const elemento = await page.$x(`//h2[text()="${descripcion}"]`);
  return elemento !== null;
}

module.exports = {
  voucherEstaVisible
};