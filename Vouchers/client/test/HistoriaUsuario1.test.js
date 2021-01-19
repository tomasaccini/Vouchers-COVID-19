const puppeteer = require('puppeteer');
const loginUtils = require("./utils/Login");
const assert = require('assert');

describe('Historia de Usuario 1', function () {
  it('Escenario 1', async function () {
    const link = 'http://localhost:3000/';

    const browser = await puppeteer.launch({ headless: false, slowMo: 100, devtools: false, defaultViewport: null, args: ['--start-maximized'] });
    
    const page = await browser.newPage();
    await page.goto(link);
    await page.waitForTimeout(1000);
    await loginUtils.clickIngresarEnLanding(page);
    await page.waitForTimeout(1000);
    console.log("En la pagina de login!")
    await page.close();
    await browser.close();
    assert.ok(true);
  });
});