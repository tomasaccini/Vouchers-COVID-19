import {SERVER_URL} from '../config';

class ReclamoAPI {
  async getReclamos(userId) {
    const url = `${SERVER_URL}reclamos/usuarios/${userId}`;
    console.log(`debug | getReclamos URL es: ${url}`);
    const response = await fetch(url);
    const reclamosDto = await response.json();
    console.log(`debug | getReclamos: `, reclamosDto);
    return this._transformarReclamos(reclamosDto);
  }

  _transformarReclamos(reclamosDto) {
    return reclamosDto.map(reclamoDto => this._transformarReclamo(reclamoDto))
  }

  _transformarReclamo(reclamoDto) {
    return {
      // TODO change title, maybe something with items !!!!
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
