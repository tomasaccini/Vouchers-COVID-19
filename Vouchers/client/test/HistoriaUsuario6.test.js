const puppeteer = require('puppeteer');
const sesionUtils = require("./utils/sesionUtils");
const navbarOpcionesUtils = require("./utils/navbarOpcionesUtils");
const misProductosUtils = require("./utils/misProductosUtils");
const misTalonariosUtils = require("./utils/misTalonariosUtils");
const comprarVouchersUtils = require("./utils/comprarVouchersUtils");
const canjearVouchersUtils = require("./utils/canjearVouchersUtils");
const vouchersConfirmablesUtils = require("./utils/vouchersConfirmablesUtils");
const historialUtils = require("./utils/historialUtils");
const reclamosUtils = require("./utils/reclamosUtils");
const utils = require("./utils/utils");
const assert = require('assert');

describe('Historia de Usuario 6', function () {
  const ts = Date.now();
  const historia_usuario = "HdU 6";
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
    await misProductosUtils.crearNuevoProducto(page, _formatearStrings("Flan"),  _formatearStrings("mixto"));
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirMisProductos(page);
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.crearNuevoTalonario(page, _formatearStrings("Promo 6"), "1", "10", "01012020", "01012022", _formatearStrings("Flan"));
    await page.waitForTimeout(velocidad*4);
    await navbarOpcionesUtils.abrirMisTalonarios(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.abrirTabPausados(page);
    await page.waitForTimeout(velocidad);
    await misTalonariosUtils.activarTalonario(page, _formatearStrings("Promo 6"));
    await page.waitForTimeout(velocidad*4);

    await sesionUtils.cerrarSesion(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'cliente1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirComprarVouchers(page);
    await page.waitForTimeout(velocidad);
    await comprarVouchersUtils.comprarVoucher(page, _formatearStrings("Promo 6"));
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirCanjearVouchers(page);
    await page.waitForTimeout(velocidad);
    assert.ok(await reclamosUtils.voucherPuedeIniciarReclamo(page, _formatearStrings("Promo 6")));
    console.log("EL RECLAMO PUEDE ABRIRSE");
    await reclamosUtils.iniciarReclamo(page, _formatearStrings("Promo 6"), `Primera queja ${_formatearStrings("Promo 6")}`);
    console.log("EL RECLAMO FUE ABIERTO");
    await page.waitForTimeout(velocidad);

    await sesionUtils.cerrarSesion(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'negocio1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirReclamos(page);
    await page.waitForTimeout(velocidad);
    assert.ok(await reclamosUtils.existeReclamoSobreVoucher(page, `${_formatearStrings("Promo 6")}`));
    assert.ok(await reclamosUtils.existeMensajeEnChat(page, `Primera queja ${_formatearStrings("Promo 6")}`));
    console.log("EL RECLAMO ES VISIBLE POR EL NEGOCIO");
    await reclamosUtils.responderReclamo(page, `Primera respuesta ${_formatearStrings("Promo 6")}`);
    console.log("EL RECLAMO FUE RESPONDIDO POR EL NEGOCIO");

    await sesionUtils.cerrarSesion(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'cliente1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirReclamos(page);
    await page.waitForTimeout(velocidad);
    assert.ok(await reclamosUtils.existeReclamoSobreVoucher(page, `${_formatearStrings("Promo 6")}`));
    console.log("EL RECLAMO ES VISIBLE POR EL CLIENTE");
    assert.ok(await reclamosUtils.existeMensajeEnChat(page, `Primera respuesta ${_formatearStrings("Promo 6")}`));
    console.log("LA RESPUESTA DEL NEGOCIO ES VISIBLE POR EL CLIENTE");
    await reclamosUtils.cerrarReclamo(page, `${_formatearStrings("Promo 6")}`);
    console.log("EL CLIENTE CERRO EL RECLAMO");

    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirReclamos(page);
    await page.waitForTimeout(velocidad);
    assert.ok(!(await reclamosUtils.existeReclamoSobreVoucher(page, `${_formatearStrings("Promo 6")}`)));
    console.log("EL RECLAMO NO ES VISIBLE POR EL CLIENTE");
    await page.waitForTimeout(velocidad);
  
    await sesionUtils.cerrarSesion(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'negocio1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirReclamos(page);
    await page.waitForTimeout(velocidad);
    assert.ok(!(await reclamosUtils.existeReclamoSobreVoucher(page, `${_formatearStrings("Promo 6")}`)));
    console.log("EL RECLAMO NO ES VISIBLE POR EL NEGOCIO");
    await page.waitForTimeout(velocidad);

    await sesionUtils.cerrarSesion(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'cliente1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirCanjearVouchers(page);
    await page.waitForTimeout(velocidad);
    assert.ok(await reclamosUtils.voucherPuedeIniciarReclamo(page, _formatearStrings("Promo 6")));
    console.log("EL RECLAMO PUEDE REABRIRSE");
    await reclamosUtils.iniciarReclamo(page, _formatearStrings("Promo 6"), `Segunda queja ${_formatearStrings("Promo 6")}`);
    console.log("EL RECLAMO FUE REABIERTO");
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirReclamos(page);
    await page.waitForTimeout(velocidad);
    assert.ok(await reclamosUtils.existeReclamoSobreVoucher(page, `${_formatearStrings("Promo 6")}`));
    console.log("EL RECLAMO ES VISIBLE POR EL CLIENTE");
    assert.ok(await reclamosUtils.existeMensajeEnChat(page, `Primera queja ${_formatearStrings("Promo 6")}`));
    assert.ok(await reclamosUtils.existeMensajeEnChat(page, `Primera respuesta ${_formatearStrings("Promo 6")}`));
    assert.ok(await reclamosUtils.existeMensajeEnChat(page, `Segunda queja ${_formatearStrings("Promo 6")}`));
    console.log("TODOS LOS MENSAJES SON VISIBLES POR EL CLIENTE");
    await page.waitForTimeout(velocidad);

    await sesionUtils.cerrarSesion(page);
    await page.waitForTimeout(velocidad);
    await sesionUtils.iniciarSesion(page, 'negocio1', 'password');
    await page.waitForTimeout(velocidad);
    await navbarOpcionesUtils.abrirReclamos(page);
    await page.waitForTimeout(velocidad);
    assert.ok(await reclamosUtils.existeReclamoSobreVoucher(page, `${_formatearStrings("Promo 6")}`));
    console.log("EL RECLAMO ES VISIBLE POR EL NEGOCIO");
    assert.ok(await reclamosUtils.existeMensajeEnChat(page, `Primera queja ${_formatearStrings("Promo 6")}`));
    assert.ok(await reclamosUtils.existeMensajeEnChat(page, `Primera respuesta ${_formatearStrings("Promo 6")}`));
    assert.ok(await reclamosUtils.existeMensajeEnChat(page, `Segunda queja ${_formatearStrings("Promo 6")}`));
    console.log("TODOS LOS MENSAJES SON VISIBLES POR EL NEGOCIO");
    await reclamosUtils.responderReclamo(page, `Segunda respuesta ${_formatearStrings("Promo 6")}`);
    console.log("EL RECLAMO FUE RESPONDIDO NUEVAMENTE POR EL NEGOCIO");

    await page.waitForTimeout(velocidad*10);
    await page.close();
    await browser.close();
    assert.ok(true);
  });
});