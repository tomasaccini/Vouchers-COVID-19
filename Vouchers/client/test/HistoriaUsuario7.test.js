const puppeteer = require('puppeteer');
const sesionUtils = require("./utils/sesionUtils");
const navbarOpcionesUtils = require("./utils/navbarOpcionesUtils");
const misProductosUtils = require("./utils/misProductosUtils");
const misTalonariosUtils = require("./utils/misTalonariosUtils");
const comprarVouchersUtils = require("./utils/comprarVouchersUtils");
const canjearVouchersUtils = require("./utils/canjearVouchersUtils");
const vouchersConfirmablesUtils = require("./utils/vouchersConfirmablesUtils");
const historialUtils = require("./utils/historialUtils")
const utils = require("./utils/utils");
const assert = require('assert');

describe('Historia de Usuario 7', function () {
  const ts = Date.now();
  const historia_usuario = "HdU 7";
  const velocidad = 1000;
  
  function _formatearStrings(texto) {
    return utils.formatearStrings(texto, historia_usuario, ts);
  }

  it('Escenario 1 - ordenar por rating', async function () {
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
    await misProductosUtils.crearNuevoProducto(page, _formatearStrings("Helado"),  _formatearStrings("Americana"));
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirMisProductos(page);
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.crearNuevoTalonario(page, _formatearStrings("Promo 7 - A"), "10", "10", "01012020", "01012022", _formatearStrings("Helado"));
    await page.waitForTimeout(velocidad*4);
    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.abrirTabPausados(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.activarTalonario(page, _formatearStrings("Promo 7 - A"));
    await page.waitForTimeout(velocidad*4);
    await misTalonariosUtils.crearNuevoTalonario(page, _formatearStrings("Promo 7 - B"), "10", "10", "01012020", "01012022", _formatearStrings("Helado"));
    await page.waitForTimeout(velocidad*4);
    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.abrirTabPausados(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.activarTalonario(page, _formatearStrings("Promo 7 - B"));
    await page.waitForTimeout(velocidad*4);


    await sesionUtils.cerrarSesion(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'cliente1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirComprarVouchers(page);
    await page.waitForTimeout(velocidad);
    await comprarVouchersUtils.comprarVoucher(page, _formatearStrings("Promo 7 - A"));
    await page.waitForTimeout(velocidad);

    await navbarOpcionesUtils.abrirComprarVouchers(page);
    await page.waitForTimeout(velocidad);
    await comprarVouchersUtils.comprarVoucher(page, _formatearStrings("Promo 7 - B"));
    await page.waitForTimeout(velocidad);
    console.log("LOS VOUCHERS FUERON CANJEADOS");

    await navbarOpcionesUtils.abrirCanjearVouchers(page);
    await page.waitForTimeout(velocidad);
    await canjearVouchersUtils.solicitarCanjeVoucher(page, _formatearStrings("Promo 7 - A"))
    await page.waitForTimeout(velocidad);
    
    await navbarOpcionesUtils.abrirCanjearVouchers(page);
    await page.waitForTimeout(velocidad);
    await canjearVouchersUtils.solicitarCanjeVoucher(page, _formatearStrings("Promo 7 - B"))
    await page.waitForTimeout(velocidad);
    console.log("SE SOLICITO CANJEAR LOS VOUCHERS");

    await sesionUtils.cerrarSesion(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'negocio1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirVouchersConfirmables(page);
    await page.waitForTimeout(velocidad);
    await vouchersConfirmablesUtils.confirmarCanjeVoucher(page, _formatearStrings("Promo 7 - A"));
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirVouchersConfirmables(page);
    await page.waitForTimeout(velocidad);
    await vouchersConfirmablesUtils.confirmarCanjeVoucher(page, _formatearStrings("Promo 7 - B"));
    await page.waitForTimeout(velocidad*4);
    console.log("LOS CANJES FUERON CONFIRMADOS");

    await sesionUtils.cerrarSesion(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'cliente1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirHistorial(page);
    await page.waitForTimeout(velocidad);
    await historialUtils.puntuarVoucher(page, _formatearStrings("Promo 7 - A"), "5");
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirHistorial(page);
    await page.waitForTimeout(velocidad);
    await historialUtils.puntuarVoucher(page, _formatearStrings("Promo 7 - B"), "1");
    await page.waitForTimeout(velocidad);
    console.log("EL CLIENTE PUNTUO LOS VOUCHERS");
    await page.waitForTimeout(velocidad);

    await navbarOpcionesUtils.abrirComprarVouchers(page);
    await page.waitForTimeout(velocidad);
    assert.ok(await comprarVouchersUtils.cmpVouchers(page, _formatearStrings("Promo 7 - A"), _formatearStrings("Promo 7 - B")));
    console.log("LOS VOUCHERS APARECEN EN EL ORDEN CORRECTO");

    await page.waitForTimeout(velocidad*10);
    await page.close();
    await browser.close();
    assert.ok(true);
  });
});