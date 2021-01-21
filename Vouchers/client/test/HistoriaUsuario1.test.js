const puppeteer = require('puppeteer');
const sesionUtils = require("./utils/sesionUtils");
const navbarOpcionesUtils = require("./utils/navbarOpcionesUtils");
const misProductosUtils = require("./utils/misProductosUtils");
const misTalonariosUtils = require("./utils/misTalonariosUtils");
const utils = require("./utils/utils");
const assert = require('assert');

ts = Date.now();
historia_usuario = "HdU 1";


function _formatearStrings(texto) {
  return utils.formatearStrings(texto, historia_usuario, ts);
}

describe('Historia de Usuario 1', function () {
  it('Escenario 1', async function () {
    const link = 'http://localhost:3000/';

    const browser = await puppeteer.launch({ headless: false, slowMo: 5, devtools: false, defaultViewport: null, args: ['--start-maximized'] });
    
    const page = await browser.newPage();
    await page.goto(link);
    await page.waitForTimeout(1000);
    await sesionUtils.clickIngresarEnLanding(page);
    await page.waitForTimeout(1000);
    await sesionUtils.iniciarSesion(page, 'negocio1', 'password');
    await page.waitForTimeout(1000);
    await navbarOpcionesUtils.abrirMisProductos(page);
    await page.waitForTimeout(1000);
    await misProductosUtils.crearNuevoProducto(page, _formatearStrings("cerveza"),  _formatearStrings("fria"));
    await page.waitForTimeout(1000);
    await navbarOpcionesUtils.abrirMisProductos(page);
    await page.waitForTimeout(1000);

    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(1000);
    await misTalonariosUtils.crearNuevoTalonario(page, _formatearStrings("Promo 1"), "1", "10", "01012020", "01012022", _formatearStrings("cerveza"));
    await page.waitForTimeout(5000);
    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(1000);
    await misTalonariosUtils.abrirTabNoActivos(page);
    await page.waitForTimeout(1000);
    await misTalonariosUtils.abrirTabActivos(page);
    await page.waitForTimeout(1000);
    await page.close();
    await browser.close();
    assert.ok(true);
  });
});