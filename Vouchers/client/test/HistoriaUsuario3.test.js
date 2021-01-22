const puppeteer = require('puppeteer');
const sesionUtils = require("./utils/sesionUtils");
const navbarOpcionesUtils = require("./utils/navbarOpcionesUtils");
const misProductosUtils = require("./utils/misProductosUtils");
const misTalonariosUtils = require("./utils/misTalonariosUtils");
const comprarVouchersUtils = require("./utils/comprarVouchersUtils");
const canjearVouchersUtils = require("./utils/canjearVouchersUtils");
const utils = require("./utils/utils");
const assert = require('assert');

describe('Historia de Usuario 3', function () {
  const ts = Date.now();
  const historia_usuario = "HdU 3";
  const velocidad = 1000;
  
  function _formatearStrings(texto) {
    return utils.formatearStrings(texto, historia_usuario, ts);
  }

  it('Escenario 1', async function () {
    const link = `http://${process.env.HOSTNAME}:${process.env.PORT}/`;
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
    await misProductosUtils.crearNuevoProducto(page, _formatearStrings("Papas"),  _formatearStrings("al horno"));
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirMisProductos(page);
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.crearNuevoTalonario(page, _formatearStrings("Promo 3"), "1", "10", "01012020", "01012022", _formatearStrings("Papas"));
    await page.waitForTimeout(velocidad*4);
    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.abrirTabPausados(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.activarTalonario(page, _formatearStrings("Promo 3"));
    await page.waitForTimeout(velocidad*4);

    await sesionUtils.cerrarSesion(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'cliente1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirComprarVouchers(page);
    await page.waitForTimeout(velocidad);
    await comprarVouchersUtils.comprarVoucher(page, _formatearStrings("Promo 3"));
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirCanjearVouchers(page);
    await page.waitForTimeout(velocidad);
    assert.ok(await canjearVouchersUtils.voucherEstaVisible(page, _formatearStrings("Promo 3")));
    console.log("EL VOUCHER ESTA LISTO PARA SER CANJEADO");
    await page.waitForTimeout(velocidad*10);
    await page.close();
    await browser.close();
    assert.ok(true);
  });
});