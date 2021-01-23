async function crearNuevoProducto(page, nombre, descripcion) {
  await page.waitForSelector('#crearNuevoProductoButton');
  await page.click('#crearNuevoProductoButton');
  await page.waitForSelector('#crearProductoNombreInput');
  await page.waitForSelector('#crearProductoDescripcionInput');
  await page.waitForSelector('#crearProductoConfirmarButton');
  await page.click('#crearProductoNombreInput');
  await page.keyboard.type(nombre);
  await page.click('#crearProductoDescripcionInput');
  await page.keyboard.type(descripcion);
  await page.click('#crearProductoConfirmarButton');
}

module.exports = {
  crearNuevoProducto
};