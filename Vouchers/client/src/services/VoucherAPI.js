import {SERVER_URL} from '../config';


class VoucherAPI {
  async getCounterfoils(userId) {
    const url = `${SERVER_URL}recommendations`;
    console.log(`debug | getCounterfoils URL is: ${url}`);
    const response = await fetch(url);
    const counterfoils = await response.json();
    return counterfoils.map((c) => this._transformCounterfoil(c));
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
}

const voucherAPI = new VoucherAPI();

export default voucherAPI;