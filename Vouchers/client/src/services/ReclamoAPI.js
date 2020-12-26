import {SERVER_URL} from '../config';

class ReclamoAPI {
  async getReclamos(usuarioId) {
    const url = `${SERVER_URL}reclamos/usuarios/${usuarioId}`;
    console.log(`debug | getReclamos URL es: ${url}`);
    const res = await fetch(url);
    const reclamosDto = await res.json();
    console.log(`debug | getReclamos: `, reclamosDto);
    return this._transformarReclamos(reclamosDto);
  }

  async enviarMensaje(reclamoId, usuarioId, mensaje) {
    const url = `${SERVER_URL}reclamos/${reclamoId}/nuevoMensaje`;
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
    console.log(reclamoId, usuarioId)
    const url = `${SERVER_URL}reclamos/${reclamoId}/cerrar`;
    console.log(`debug | cerrarReclamo URL es: ${url}`);
    const res = await fetch(url, {
      method: 'POST',
      body: JSON.stringify({ usuarioId: usuarioId })
    });

    if (res.status !== 200) {
      window.alert(res.message);
      return null;
    }

    const nuevoMensaje = await res.json();
    console.log(`debug | cerrarReclamo: `, nuevoMensaje);
    return nuevoMensaje;
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
      negocioEmail: reclamoDto.negocioEmail,
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
