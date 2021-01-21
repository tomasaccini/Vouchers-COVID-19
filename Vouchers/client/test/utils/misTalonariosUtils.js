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

module.exports = {
  crearNuevoTalonario
};