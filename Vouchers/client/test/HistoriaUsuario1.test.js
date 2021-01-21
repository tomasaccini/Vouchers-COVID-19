const puppeteer = require('puppeteer');
const sesionUtils = require("./utils/sesionUtils");
const navbarOpcionesUtils = require("./utils/navbarOpcionesUtils");
const misProductosUtils = require("./utils/misProductosUtils");
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

    const browser = await puppeteer.launch({ headless: false, slowMo: 50, devtools: false, defaultViewport: null, args: ['--start-maximized'] });
    
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
    await page.close();
    await browser.close();
    assert.ok(true);
  });
});