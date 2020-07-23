import {SERVER_URL} from '../config';


class VoucherAPI {
  async getCounterfoils(userId) {
    const url = `${SERVER_URL}recommendations?userId=${userId}`;
    console.log(`debug | getCounterfoils URL is: ${url}`);
    const response = await fetch(url);
    const counterfoils = await response.json();
    console.log(`debug | getCounterfoils: `, counterfoils);
    return counterfoils.map((c) => this._transformCounterfoil(c));
  }

  async getVouchers(userId) {
    const url = `${SERVER_URL}vouchers?userId=${userId}`;
    console.log(`debug | getVouchers URL is: ${url}`);
    const response = await fetch(url);
    const vouchers = await response.json();
    console.log(`debug | getVouchers: `, vouchers);
    return vouchers.map((v) => this._transformVoucher(v));
  }

  async buyVoucher(userId, counterfoilId) {
    const url = `${SERVER_URL}vouchers`;
    console.log(`debug | buyVoucher URL is: ${url}`);
    const response = await fetch(url, {
      method: 'POST',
      body: JSON.stringify({ clientId: userId, counterfoilId: counterfoilId })
    });
    const voucher = await response.json();
    console.log(`debug | bough voucher: `, voucher);
    return this._transformVoucher(voucher);
  }

  _transformCounterfoil(counterfoil) {
    const vi = counterfoil.voucherInformationCommand;

    return {
      // TODO change title, maybe something with items !!!!
      'Title': '2 Hamburguesas',
      'Description': vi.description,
      'Price': vi.price,
      // TODO format dates from 2020-08-01T03:00:00Z to 2020-08-01 !!!!
      'CreationDate': vi.validFrom,
      'EndDate': vi.validUntil,
      'Stock': counterfoil.stock,
      // TODO no more owner !!!!
      'isOwned': false,
      // TODO not bringing business name yet
      'shopName': 'Demo Restaurant'
    }
  }

  _transformVoucher(voucher) {
    const vi = voucher.voucherInformationCommand
    
    return {
      'Title': '2 Hamburguesas',
      'Description': vi.description,
      'Price': vi.price,
      // TODO format dates from 2020-08-01T03:00:00Z to 2020-08-01 !!!!
      'CreationDate': vi.validFrom,
      'EndDate': vi.validUntil,
      'Stock': voucher.stock,
      // TODO no more owner !!!!
      'isOwned': true,
      // TODO we don't have the information yet
      'shopName': '!!!! MMMM'
    }
  }
}

const voucherAPI = new VoucherAPI();

export default voucherAPI;