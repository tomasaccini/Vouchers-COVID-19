const puppeteer = require('puppeteer');
const sesionUtils = require("./utils/sesionUtils");
const navbarOpcionesUtils = require("./utils/navbarOpcionesUtils");
const assert = require('assert');

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
    await navbarOpcionesUtils.abrirMiNegocio(page);
    await page.waitForTimeout(1000);
    await navbarOpcionesUtils.abrirVouchersConfirmables(page);
    await page.waitForTimeout(1000);
    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(1000);
    await navbarOpcionesUtils.abrirMisProductos(page);
    await page.waitForTimeout(1000);
    await navbarOpcionesUtils.abrirReclamos(page);
    await page.waitForTimeout(1000);
    await page.close();
    await browser.close();
    assert.ok(true);
  });
});