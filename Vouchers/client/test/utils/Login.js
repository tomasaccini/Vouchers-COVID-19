async function clickIngresarEnLanding(page) {
  await page.waitForSelector('#LandingIngresarButton');
  await page.click('#LandingIngresarButton');
}

async function iniciarSesion(page, email, contrasenia) {
  await page.waitForSelector('#email');
  await page.waitForSelector('#contrasenia');
  await page.waitForSelector('#iniciarSesionButton');
  await page.click('#email');
  await page.keyboard.type(email);
  await page.click('#contrasenia');
  await page.keyboard.type(contrasenia);
  await page.click('#iniciarSesionButton');
}

module.exports = {
  clickIngresarEnLanding,
  iniciarSesion
};