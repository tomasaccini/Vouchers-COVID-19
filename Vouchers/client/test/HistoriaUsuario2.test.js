const puppeteer = require('puppeteer');
const sesionUtils = require("./utils/sesionUtils");
const navbarOpcionesUtils = require("./utils/navbarOpcionesUtils");
const misProductosUtils = require("./utils/misProductosUtils");
const misTalonariosUtils = require("./utils/misTalonariosUtils");
const comprarVouchersUtils = require("./utils/comprarVouchersUtils");
const utils = require("./utils/utils");
const assert = require('assert');

describe('Historia de Usuario 2', function () {
  const ts = Date.now();
  const historia_usuario = "HdU 2";
  const velocidad = 1000;
  
  function _formatearStrings(texto) {
    return utils.formatearStrings(texto, historia_usuario, ts);
  }

  it('Escenario 1', async function () {
    const link = `http://localhost:${process.env.PORT}/`;
    const headless = process.env.PUPPETEER_HEADLESS == 'true';
    const browser = await puppeteer.launch({ headless: headless, slowMo: 5, devtools: false, defaultViewport: null, args: ['--start-maximized'] });
    
    const page = await browser.newPage();
    await page.goto(link);
    await page.waitForTimeout(velocidad);
    await sesionUtils.clickIngresarEnLanding(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'negocio1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirMisProductos(page);
    await page.waitForTimeout(velocidad);
    await misProductosUtils.crearNuevoProducto(page, _formatearStrings("hamburguesa"),  _formatearStrings("vegana"));
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirMisProductos(page);
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.crearNuevoTalonario(page, _formatearStrings("Promo 2"), "1", "10", "01012020", "01012022", _formatearStrings("hamburguesa"));
    await page.waitForTimeout(velocidad*4);
    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.abrirTabPausados(page);
    assert.ok(await misTalonariosUtils.talonarioEstaVisible(page, _formatearStrings("Promo 2")));
    console.log("EL TALONARIO ESTA PAUSADO");
    await page.waitForTimeout(velocidad*10);

    await sesionUtils.cerrarSesion(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'cliente1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirComprarVouchers(page);
    await page.waitForTimeout(velocidad);

    assert.ok(!(await comprarVouchersUtils.voucherEstaVisible(page, _formatearStrings("Promo 2"))));
    console.log("EL VOUCHER PAUSADO NO ESTA VISIBLE");
    await page.waitForTimeout(velocidad*10);
    await page.close();
    await browser.close();
    assert.ok(true);
  });
});