async function clickIngresarEnLanding(page) {
  await page.waitForSelector('#LandingIngresarButton');
  await page.click('#LandingIngresarButton');
}

module.exports = {
  clickIngresarEnLanding
};