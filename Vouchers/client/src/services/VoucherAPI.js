import {SERVER_URL} from '../config';
import { format } from 'date-fns';


class VoucherAPI {
  async getTalonarios(userId) {
    const url = `${SERVER_URL}recommendations?userId=${userId}`;
    console.log(`debug | getTalonarios URL is: ${url}`);
    const res = await fetch(url);
    const counterfoils = await res.json();
    console.log(`debug | getTalonarios: `, counterfoils);
    return counterfoils.map((c) => this._transformarTalonario(c));
  }

  // Returns vouchers owned by clients
  // TODO: Modify and rename
  async getVouchers(userId) {
    const url = `${SERVER_URL}vouchers?userId=${userId}`;
    console.log(`debug | getVouchers URL is: ${url}`);
    const res = await fetch(url);
    const vouchers = await res.json();
    console.log(`debug | getVouchers: `, vouchers);
    return vouchers.map((v) => this._transformarVoucher(v));
  }

  async comprarVoucher(userId, counterfoilId) {
    const url = `${SERVER_URL}vouchers`;
    console.log(`debug | comprarVoucher URL is: ${url}`);
    const res = await fetch(url, {
      method: 'POST',
      body: JSON.stringify({ clientId: userId, counterfoilId: counterfoilId })
    });
    const voucher = await res.json();
    console.log(`debug | compro voucher: `, voucher);
    return this._transformarVoucher(voucher);
  }

  _transformarTalonario(counterfoil) {
    const vi = counterfoil.voucherInformationCommand;

    const desde = new Date(vi.validoDesde);
    const hasta = new Date(vi.validoHasta);

    return {
      'titulo': '2 Hamburguesas',
      'descripcion': vi.descripcion,
      'precio': vi.precio,
      'validoDesde': format(desde, 'yyyy/MM/dd'),
      'validoHasta': format(hasta, 'yyyy/MM/dd'),
      'stock': vi.stock,
      // TODO no more owner !!!!
      'isOwned': true,
      // TODO we don't have the information yet
      'nombreNegocio': 'PLACEHOLDER NOMBRE NEGOCIO'
    }
  }

  _transformarVoucher(voucher) {
    const vi = voucher.info

    const desde = new Date(vi.validoDesde);
    const hasta = new Date(vi.validoHasta);

    return {
      'titulo': '2 Hamburguesas',
      'descripcion': vi.descripcion,
      'precio': vi.precio,
      'validoDesde': format(desde, 'yyyy/MM/dd'),
      'validoHasta': format(hasta, 'yyyy/MM/dd'),
      'stock': voucher.stock,
      // TODO no more owner !!!!
      'isOwned': true,
      // TODO we don't have the information yet
      'nombreNegocio': 'PLACEHOLDER NOMBRE NEGOCIO'
    }
  }
}

const voucherAPI = new VoucherAPI();

export default voucherAPI;