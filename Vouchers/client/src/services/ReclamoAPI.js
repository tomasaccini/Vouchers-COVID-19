import {SERVER_URL} from '../config';

class ReclamoAPI {
  async getReclamos(usuarioId) {
    const url = `${SERVER_URL}/reclamos/usuarios/${usuarioId}`;
    console.log(`debug | getReclamos URL es: ${url}`);
    const res = await fetch(url);
    const reclamosDto = await res.json();
    console.log(`debug | getReclamos: `, reclamosDto);
    return this._transformarReclamos(reclamosDto);
  }

  async enviarMensaje(reclamoId, usuarioId, mensaje) {
    const url = `${SERVER_URL}/reclamos/${reclamoId}/nuevoMensaje`;
    console.log(`debug | enviarMensaje URL es: ${url}`);
    const res = await fetch(url, {
      method: 'POST',
      body: JSON.stringify({ usuarioId: usuarioId, mensaje: mensaje })
    });
    const nuevoMensaje = await res.json();
    console.log(`debug | enviarMensaje: `, nuevoMensaje);
    return nuevoMensaje;
  }

  async cerrarReclamo(reclamoId, usuarioId) {
    const url = `${SERVER_URL}/reclamos/${reclamoId}/cerrar`;
    console.log(`debug | cerrarReclamo URL es: ${url}`);
    const res = await fetch(url, {
      method: 'POST',
      body: JSON.stringify({ usuarioId: usuarioId })
    });

    if (res.status !== 200) {
      window.alert(res.message);
      return null;
    }

    const reclamoCerrado = await res.json();
    console.log(`debug | cerrarReclamo: `, reclamoCerrado);
    return reclamoCerrado;
  }

  async abrirReclamo(voucherId, descripcion, usuarioId) {
    const url = `${SERVER_URL}/reclamos`;
    console.log(`debug | abrirReclamo URL es: ${url}`);
    const res = await fetch(url, {
      method: 'POST',
      // TODO: agregar descripcion/primer mensaje !!!!
      body: JSON.stringify({ voucherId: voucherId, descripcion: descripcion })
    });

    if (res.status !== 200) {
      // !!!! window.alert(res.message);
      return null;
    }

    const reclamoIniciado = await res.json();
    console.log(`debug | abrirReclamo: `, reclamoIniciado);
    return reclamoIniciado;
  }


  _transformarReclamos(reclamosDto) {
    return reclamosDto.map(reclamoDto => this._transformarReclamo(reclamoDto))
  }

  _transformarReclamo(reclamoDto) {
    return {
      id: reclamoDto.id,
      clienteId: reclamoDto.clienteId,
      clienteEmail: reclamoDto.clienteEmail,
      negocioId: reclamoDto.negocioId,
      negocioNombre: reclamoDto.negocioNombre,
      negocioEmail: reclamoDto.negocioEmail,
      voucherDescripcion: reclamoDto.voucherDescripcion,
      state: reclamoDto.estado,
      fechaUltimoMensaje: reclamoDto.fechaUltimoMensaje,
      mensajes: reclamoDto.mensajes.map(mensajeDto => this._transformarMensaje(mensajeDto)),
    }
  }

  _transformarMensaje(mensajeDto) {
    return {
      duenioId: mensajeDto.duenioId,
      duenioEmail: mensajeDto.duenioEmail,
      fecha: mensajeDto.fecha,
      texto: mensajeDto.texto,
    }
  }
}

const reclamoAPI = new ReclamoAPI();

export default reclamoAPI;
