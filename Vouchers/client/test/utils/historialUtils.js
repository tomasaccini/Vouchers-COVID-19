async function cambiarACanjeadosTab(page) {
  await page.waitForSelector('#historialCanjeadosTab');
  await page.click('#historialCanjeadosTab');
}

async function cambiarAExpiradosTab(page) {
  await page.waitForSelector('#historialExpiradosTab');
  await page.click('#historialExpiradosTab');
}

async function voucherEstaVisible(page, descripcion) {
  const elemento = await page.$x(`//h2[text()="${descripcion}"]`);
  return elemento.length > 0;
}

async function puntuarVoucher(page, descripcion, rating) {
  const puntajeSelector = `//h2[text()="${descripcion}"]/..//label[contains(@for, "unique-rating") and contains(@for, "-${rating}")]`;
  await page.waitForXPath(puntajeSelector);
  const puntaje = await page.$x(puntajeSelector);
  await puntaje[0].click();
  await page.waitForTimeout(2000);
}

module.exports = {
  cambiarACanjeadosTab,
  cambiarAExpiradosTab,
  voucherEstaVisible,
  puntuarVoucher
};