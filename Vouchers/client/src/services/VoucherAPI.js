import {SERVER_URL} from '../config';


class VoucherAPI {
  async getCounterfoils(userId) {
    const url = `${SERVER_URL}recommendations?userId=${userId}`;
    console.log(`debug | getCounterfoils URL is: ${url}`);
    const response = await fetch(url);
    const counterfoils = await response.json();
    return counterfoils.map((c) => this._transformCounterfoil(c));
  }

  async getVouchers(userId) {
    const url = `${SERVER_URL}vouchers?userId=${userId}`;
    console.log(`debug | getVouchers URL is: ${url}`);
    const response = await fetch(url);
    const vouchers = await response.json();
    console.log('!!!!', vouchers)
    return vouchers.map((v) => this._transformVoucher(v));
  }

  async buyVouchers(userId, counterfoilId) {
    const url = `${SERVER_URL}vouchers`;
    console.log(`debug | getVouchers URL is: ${url}`);
    const response = await fetch(url, {
      method: 'POST',
      body: JSON.stringify({ userId, counterfoilId })
    });
    const voucher = await response.json();
    return this._transformVoucher(voucher);
  }

  _transformCounterfoil(counterfoil) {
    console.log(`debug | _transformCounterfoil ${JSON.stringify(counterfoil)}`);
    const vi = counterfoil.voucherInformation;
    return {
      // TODO change title, maybe something with items !!!!
      'Title': '2 Hamburguesas',
      'Description': `${counterfoil.business.name} - ${vi.description}`,
      'Price': vi.price,
      // TODO format dates from 2020-08-01T03:00:00Z to 2020-08-01 !!!!
      'CreationDate': vi.validFrom,
      'EndDate': vi.validUntil,
      'Stock': counterfoil.stock,
      // TODO no more owner !!!!
      'isOwned': false,
      'shopName': counterfoil.business.name
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