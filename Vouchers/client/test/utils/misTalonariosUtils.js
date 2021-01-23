async function crearNuevoTalonario(page, descripcion, stock, precio, desde, hasta, nombre_producto) {
  await page.waitForSelector('#crearNuevoTalonarioButton');
  await page.click('#crearNuevoTalonarioButton');
  await page.waitForSelector('#descripcionTalonario');
  await page.waitForSelector('#stockTalonario');
  await page.waitForSelector('#precioTalonario');
  await page.waitForSelector('#desdeTalonario');
  await page.waitForSelector('#hastaTalonario');
  await page.waitForSelector('#precioTalonario');
  await page.waitForSelector('#crearTalonarioConfirmarButton');
  await page.click('#descripcionTalonario');
  await page.keyboard.type(descripcion);
  await page.click('#stockTalonario');
  await page.keyboard.type(stock);
  await page.click('#precioTalonario');
  await page.keyboard.type(precio);
  await page.click('#desdeTalonario');
  await page.keyboard.type(desde);
  await page.click('#hastaTalonario');
  await page.keyboard.type(hasta);
  await page.waitForXPath(`//h3[text()="${nombre_producto}"]/..//input`);
  const elements = await page.$x(`//h3[text()="${nombre_producto}"]/..//input`);
  await elements[0].click();
  await page.keyboard.type("1");
  await page.keyboard.press('Enter');
}

async function abrirTabActivos(page) {
  await page.waitForSelector('#TalonariosActivosTab');
  await page.click('#TalonariosActivosTab');
}

async function abrirTabPausados(page) {
  await page.waitForSelector('#TalonariosPausadosTab');
  await page.click('#TalonariosPausadosTab');
}

async function activarTalonario(page, descripcion) {
  const activarBotonSelector = `//h2[text()="${descripcion}"]/../button[span[text()="Activar"]]`;
  const confirmarCambioSelector = `//h5[contains(text(), "${descripcion}?")]/../../..//button[@id="ConfirmarCambioTalonario"]`;
  await page.waitForXPath(activarBotonSelector);
  const activarBoton = await page.$x(activarBotonSelector);
  await activarBoton[0].click();
  await page.waitForTimeout(2000);
  await page.waitForXPath(confirmarCambioSelector);
  const confirmarCambio = await page.$x(confirmarCambioSelector);
  await confirmarCambio[0].click();
}

async function pausarTalonario(page, descripcion) {
  const pausarBotonSelector = `//h2[text()="${descripcion}"]/../button[span[text()="Pausar"]]`;
  const confirmarCambioSelector = `//h5[contains(text(), "${descripcion}?")]/../../..//button[@id="ConfirmarCambioTalonario"]`;
  await page.waitForXPath(pausarBotonSelector);
  const pausarBoton = await page.$x(pausarBotonSelector);
  await pausarBoton[0].click();
  await page.waitForTimeout(2000);
  await page.waitForXPath(confirmarCambioSelector);
  const confirmarCambio = await page.$x(confirmarCambioSelector);
  await confirmarCambio[0].click();
}

async function talonarioEstaVisible(page, descripcion) {
  const elemento = await page.$x(`//h2[text()="${descripcion}"]`);
  return elemento.length > 0;
}

module.exports = {
  crearNuevoTalonario,
  abrirTabActivos,
  abrirTabPausados,
  activarTalonario,
  pausarTalonario,
  talonarioEstaVisible
};