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

  async getTalonariosPorNegocio(negocioId){
    const url = `${SERVER_URL}negocios/obtenerTalonarios/${negocioId}`;
    console.log(`debug | getTalonariosPorNegocio URL is: ${url}`);
    const res = await fetch(url);
    const counterfoils = await res.json();
    console.log(`debug | getTalonariosPorNegocio: `, counterfoils);
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

  async comprarVoucher(clienteId, talonarioId) {
    const url = `${SERVER_URL}talonarios/comprar`;
    console.log(`debug | comprarVoucher URL is: ${url}`);
    const res = await fetch(url, {
      method: 'POST',
      body: JSON.stringify({ clienteId: clienteId, talonarioId: talonarioId })
    });

    console.log(res);
    if (res.status !== 200 && res.status !== 201) {
      window.alert(res.message);
      return null;
    }

    const voucher = await res.json();
    console.log(`debug | compro voucher: `, voucher);
    return this._transformarVoucher(voucher);
  }

  _transformarItems(item) {
    return {
      'cantidad': item.cantidad,
      'nombre':  item.producto.nombre,
      'descripcion':  item.producto.descripcion
    }
  }

  _transformarTalonario(talonario) {
    const info = talonario.info;

    const desde = new Date(info.validoDesde);
    const hasta = new Date(info.validoHasta);

    return {
      'id': talonario.id,
      'titulo': '2 Hamburguesas',
      'descripcion': info.descripcion,
      'precio': info.precio,
      'validoDesde': format(desde, 'yyyy/MM/dd'),
      'validoHasta': format(hasta, 'yyyy/MM/dd'),
      'stock': talonario.stock,
      'items': info.items.map((i) => this._transformarItems(i)),
      // TODO no more owner !!!!
      'isOwned': false,
      // TODO we don't have the information yet
      'nombreNegocio': talonario.nombre
    }
  }

  _transformarVoucher(voucher) {
    const vi = voucher.info

    const desde = new Date(vi.validoDesde);
    const hasta = new Date(vi.validoHasta);

    return {
      'id': vi.id,
      'titulo': '2 Hamburguesas',
      'descripcion': vi.descripcion,
      'precio': vi.precio,
      'validoDesde': format(desde, 'yyyy/MM/dd'),
      'validoHasta': format(hasta, 'yyyy/MM/dd'),
      'stock': voucher.stock,
      // TODO no more owner !!!!
      'isOwned': true,
      // TODO we don't have the information yet
      'nombreNegocio': voucher.negocio.nombre
    }
  }
}

const voucherAPI = new VoucherAPI();

export default voucherAPI;
