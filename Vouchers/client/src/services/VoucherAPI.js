import {SERVER_URL} from '../config';
import { format } from 'date-fns';


class VoucherAPI {

  async getTalonarios(userId) {
    const url = `${SERVER_URL}/recommendations?userId=${userId}`;
    console.log(`debug | getTalonarios URL is: ${url}`);
    const res = await fetch(url);
    const talonarios = await res.json();
    console.log(`debug | getTalonarios: `, talonarios);
    return talonarios.map((t) => this._transformarTalonario(t)).filter((t) => t.activo);
  }

  async getTalonariosPorNegocio(negocioId) {
    const url = `${SERVER_URL}/negocios/obtenerTalonarios/${negocioId}`;
    console.log(`debug | getTalonariosPorNegocio URL is: ${url}`);
    const res = await fetch(url);
    const talonarios = await res.json();
    console.log(`debug | getTalonariosPorNegocio: `, talonarios);
    return talonarios.map((t) => this._transformarTalonario(t));
  }

  async getVouchersConfirmablesPorNegocio(negocioId) {
    const url = `${SERVER_URL}/vouchers/obtenerConfirmables/${negocioId}`;
    console.log(`debug | getVouchersConfirmablesPorNegocio URL is: ${url}`);
    const res = await fetch(url);
    const vouchers = await res.json();
    console.log(`debug | getVouchersConfirmablesPorNegocio: `, vouchers);
    return vouchers.map((v) => this._transformarVoucher(v));
  }


  // Returns vouchers owned by clients
  // TODO: Modify and rename
  async getVouchers(userId) {
    // const url = `${SERVER_URL}/vouchers?userId=${userId}`;
    const url = `${SERVER_URL}/vouchers/obtenerPorUsuario/${userId}?max=100`;
    console.log(`debug | getVouchers URL is: ${url}`);
    const res = await fetch(url);
    const vouchers = await res.json();
    console.log(`debug | getVouchers: `, vouchers);
    return vouchers.map((v) => this._transformarVoucher(v));
  }

  async getVouchersConEstado(userId, estado) {
    const url = `${SERVER_URL}/vouchers/obtenerPorUsuario/${userId}?estado=${estado}`;
    console.log(`debug | getVouchers URL is: ${url}`);
    const res = await fetch(url);
    const vouchers = await res.json();
    console.log(`debug | getVouchers: `, vouchers);
    return vouchers.map((v) => this._transformarVoucher(v));
  }

  async comprarVoucher(clienteId, talonarioId) {
    const url = `${SERVER_URL}/talonarios/comprar`;
    console.log(`debug | comprarVoucher URL is: ${url}`);
    const res = await fetch(url, {
      method: 'POST',
      body: JSON.stringify({ clienteId: clienteId, talonarioId: talonarioId })
    });

    if (res.status !== 200 && res.status !== 201) {
      window.alert(res.message);
      return null;
    }

    const voucher = await res.json();
    console.log(`debug | compro voucher: `, voucher);
    return this._transformarVoucher(voucher);
  }

  async confirmarCanje(voucherId, negocioId) {
    const url = `${SERVER_URL}/vouchers/confirmarCanje/${voucherId}`;
    console.log(`debug | confirmarCanje URL is: ${url}`);
    const res = await fetch(url, {
      method: 'POST',
      body: JSON.stringify({ negocioId: negocioId })
    });

    if (res.status !== 200) {
      window.alert(res.message);
      return null;
    }

    const voucher = await res.json();
    console.log(`debug | confirmarCanje: `, voucher);
    return this._transformarVoucher(voucher);
  }

  async solicitarCanje(voucherId, clienteId) {
    const url = `${SERVER_URL}/vouchers/solicitarCanje/${voucherId}`;
    console.log(`debug | solicitarCanje URL is: ${url}`);
    const res = await fetch(url, {
      method: 'POST',
      body: JSON.stringify({ clienteId: clienteId })
    });

    if (res.status !== 200) {
      window.alert(res.message);
      return null;
    }

    const voucher = await res.json();
    console.log(`debug | solicitarCanje: `, voucher);
    return this._transformarVoucher(voucher);
  }

  async cancelarSolicitudDeCanje(voucherId, clienteId) {
    const url = `${SERVER_URL}/vouchers/cancelarSolicitudDeCanje/${voucherId}`;
    console.log(`debug | cancelarSolicitudDeCanje URL is: ${url}`);
    const res = await fetch(url, {
      method: 'POST',
      body: JSON.stringify({ clienteId: clienteId })
    });

    if (res.status !== 200) {
      window.alert(res.message);
      return null;
    }

    const voucher = await res.json();
    console.log(`debug | cancelarSolicitudDeCanje: `, voucher);
    return this._transformarVoucher(voucher);
  }

  async puntuarVoucher(voucherId, rating) {
    const url = `${SERVER_URL}/vouchers/cambiarRating`;
    console.log(`debug | cambiarRatingVoucher URL is: ${url} para ${voucherId}`);
    const res = await fetch(url, {
      method: 'POST',
      body: JSON.stringify({ 
        voucherId: voucherId,
        rating: rating
      })
    });

    if (res.status !== 200) {
      window.alert(res.message);
      return null;
    }

    const voucher = await res.json();
    console.log(`debug | puntuarVoucher: `, voucher);
    return this._transformarVoucher(voucher);
  }

  _transformarItems(item) {
    return {
      'cantidad': item.cantidad,
      'nombre':  item.productCommand.nombre,
      'descripcion':  item.productCommand.descripcion
    }
  }

  _transformarTalonario(talonario) {
    console.log('!!!! _transformarTalonario', talonario)

    const iv = talonario.informacionVoucherCommand;
    const items = iv.itemsCommand ? iv.itemsCommand : [];

    const desde = new Date(iv.validoDesde);
    const hasta = new Date(iv.validoHasta);

    return {
      'id': talonario.id,
      'titulo': iv.descripcion,
      'descripcion': iv.descripcion,
      'precio': iv.precio,
      'validoDesde': format(desde, 'yyyy/MM/dd'),
      'validoHasta': format(hasta, 'yyyy/MM/dd'),
      'stock': talonario.stock,
      'items': items.map((i) => this._transformarItems(i)),
      'activo': talonario.activo,
      'negocioNombre': talonario.negocioNombre,
      'negocioId': talonario.negocioId,
    }
  }

  _transformarVoucher(voucher) {
    const iv = voucher.informacionVoucherCommand

    const desde = new Date(iv.validoDesde);
    const hasta = new Date(iv.validoHasta);

    return {
      'id': voucher.id,
      'titulo': iv.descripcion,
      'descripcion': iv.descripcion,
      'precio': iv.precio,
      'validoDesde': format(desde, 'yyyy/MM/dd'),
      'validoHasta': format(hasta, 'yyyy/MM/dd'),
      'negocioNombre': voucher.negocioCommand.nombre,
      'negocioId': voucher.negocioCommand.id,
      'estado': voucher.estado,
      'rating': voucher.rating,
      'reclamoAbierto': voucher.reclamoAbierto,
      'clienteEmail': voucher.clienteEmail,
    }
  }
}

const voucherAPI = new VoucherAPI();

export default voucherAPI;
