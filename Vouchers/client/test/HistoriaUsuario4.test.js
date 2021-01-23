const puppeteer = require('puppeteer');
const sesionUtils = require("./utils/sesionUtils");
const navbarOpcionesUtils = require("./utils/navbarOpcionesUtils");
const misProductosUtils = require("./utils/misProductosUtils");
const misTalonariosUtils = require("./utils/misTalonariosUtils");
const comprarVouchersUtils = require("./utils/comprarVouchersUtils");
const canjearVouchersUtils = require("./utils/canjearVouchersUtils");
const vouchersConfirmablesUtils = require("./utils/vouchersConfirmablesUtils");
const utils = require("./utils/utils");
const assert = require('assert');

describe('Historia de Usuario 4', function () {
  const ts = Date.now();
  const historia_usuario = "HdU 4";
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
    await misProductosUtils.crearNuevoProducto(page, _formatearStrings("Pizza"),  _formatearStrings("al horno"));
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirMisProductos(page);
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.crearNuevoTalonario(page, _formatearStrings("Promo 4"), "1", "10", "01012020", "01012022", _formatearStrings("Pizza"));
    await page.waitForTimeout(velocidad*4);
    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.abrirTabPausados(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.activarTalonario(page, _formatearStrings("Promo 4"));
    await page.waitForTimeout(velocidad*4);

    await sesionUtils.cerrarSesion(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'cliente1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirComprarVouchers(page);
    await page.waitForTimeout(velocidad);
    await comprarVouchersUtils.comprarVoucher(page, _formatearStrings("Promo 4"));
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirCanjearVouchers(page);
    await page.waitForTimeout(velocidad);
    await canjearVouchersUtils.solicitarCanjeVoucher(page, _formatearStrings("Promo 4"))
    await page.waitForTimeout(velocidad);
    assert.ok(await canjearVouchersUtils.voucherEstaPendienteDeConfirmacion(page, _formatearStrings("Promo 4")));
    console.log("EL VOUCHER ESTA PENDIENTE DE CONFIRMACION EN LA VISTA DEL CLIENTE");
    await page.waitForTimeout(velocidad);
    assert.ok(!(await canjearVouchersUtils.voucherPuedeSerCanjeado(page, _formatearStrings("Promo 4"))));
    console.log("EL VOUCHER NO PUEDE SER CANJEADO");
    
    await page.waitForTimeout(velocidad);
    await sesionUtils.cerrarSesion(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'negocio1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirVouchersConfirmables(page);
    await page.waitForTimeout(velocidad);
    assert.ok(await vouchersConfirmablesUtils.voucherEstaPendienteDeConfirmacion(page, _formatearStrings("Promo 4")));
    console.log("EL VOUCHER ESTA PENDIENTE DE CONFIRMACION EN LA VISTA DEL NEGOCIO");
    
    await page.waitForTimeout(velocidad*10);
    await page.close();
    await browser.close();
    assert.ok(true);
  });
});