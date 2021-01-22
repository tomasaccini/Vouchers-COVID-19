const puppeteer = require('puppeteer');
const sesionUtils = require("./utils/sesionUtils");
const navbarOpcionesUtils = require("./utils/navbarOpcionesUtils");
const misProductosUtils = require("./utils/misProductosUtils");
const misTalonariosUtils = require("./utils/misTalonariosUtils");
const comprarVouchersUtils = require("./utils/comprarVouchersUtils");
const utils = require("./utils/utils");
const assert = require('assert');

ts = Date.now();
historia_usuario = "HdU 1";
velocidad = 1000;


function _formatearStrings(texto) {
  return utils.formatearStrings(texto, historia_usuario, ts);
}

describe('Historia de Usuario 1', function () {
  it('Escenario 1', async function () {
    const link = 'http://localhost:3000/';

    const browser = await puppeteer.launch({ headless: false, slowMo: 5, devtools: false, defaultViewport: null, args: ['--start-maximized'] });
    
    const page = await browser.newPage();
    await page.goto(link);
    await page.waitForTimeout(velocidad);
    await sesionUtils.clickIngresarEnLanding(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'negocio1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirMisProductos(page);
    await page.waitForTimeout(velocidad);
    await misProductosUtils.crearNuevoProducto(page, _formatearStrings("cerveza"),  _formatearStrings("fria"));
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirMisProductos(page);
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.crearNuevoTalonario(page, _formatearStrings("Promo 1"), "1", "10", "01012020", "01012022", _formatearStrings("cerveza"));
    await page.waitForTimeout(velocidad*4);
    await misTalonariosUtils.abrirTabPausados(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.activarTalonario(page, _formatearStrings("Promo 1"));
    await page.waitForTimeout(velocidad*4);

    await sesionUtils.cerrarSesion(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'cliente1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirComprarVouchers(page);
    await page.waitForTimeout(velocidad);

    assert.ok(comprarVouchersUtils.voucherEstaVisible(_formatearStrings("Promo 1")));
    console.log("EL VOUCHER ACTIVO ESTA VISIBLE");
    await page.waitForTimeout(velocidad*10);
    await page.close();
    await browser.close();
    assert.ok(true);
  });
});