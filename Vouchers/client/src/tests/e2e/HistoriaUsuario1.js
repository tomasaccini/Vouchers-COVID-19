const puppeteer = require('puppeteer');

(async event => {
  const link = 'http://localhost:3000/';

  const browser = await puppeteer.launch({ headless: false, slowMo: 100, devtools: false, defaultViewport: null, args: ['--start-maximized'] });

  try {
    const page = await browser.newPage();
    await page.goto(link);
    await page.waitFor(10000);

    console.log("DEBUG!")

    await page.waitFor(10000);

    await page.close();
    await browser.close();
  } catch (error) {
    console.log(error);
    await browser.close();
  }
})();