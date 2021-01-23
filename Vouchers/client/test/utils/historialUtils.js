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

module.exports = {
  cambiarACanjeadosTab,
  cambiarAExpiradosTab,
  voucherEstaVisible
};