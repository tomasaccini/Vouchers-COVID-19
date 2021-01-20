async function clickOpciones(page) {
  await page.waitForSelector('#navbarOpciones');
  await page.click('#navbarOpciones');
}

async function abrirMiNegocio(page) {
  await clickOpciones(page);
  await page.waitForSelector('#miNegocioOpcionNavbar');
  await page.click('#miNegocioOpcionNavbar');
}

async function abrirMisTalonarios(page) {
  await clickOpciones(page);
  await page.waitForSelector('#misTalonariosOpcionNavbar');
  await page.click('#misTalonariosOpcionNavbar');
}

async function abrirVouchersConfirmables(page) {
  await clickOpciones(page);
  await page.waitForSelector('#vouchersConfirmablesOpcionNavbar');
  await page.click('#vouchersConfirmablesOpcionNavbar');
}

async function abrirMisProductos(page) {
  await clickOpciones(page);
  await page.waitForSelector('#misProductosOpcionNavbar');
  await page.click('#misProductosOpcionNavbar');
}

async function abrirReclamos(page) {
  await clickOpciones(page);
  await page.waitForSelector('#reclamosOpcionNavbar');
  await page.click('#reclamosOpcionNavbar');
}

module.exports = {
  abrirMiNegocio,
  abrirMisTalonarios,
  abrirVouchersConfirmables,
  abrirMisProductos,
  abrirReclamos
};