import {SERVER_URL} from '../config';


class ClienteAPI {

  async getCliente(clienteId){
    const url = `${SERVER_URL}/clientes/${clienteId}`;
    const res = await fetch(url);
    const cliente = await res.json();
    console.log(cliente);
    return this._transformarCliente(cliente)
  }

  _transformarCliente(c) {
    return {
      'nombreCompleto': c.nombreCompleto,
      'numeroTelefonico': c.numeroTelefonico,
      'email': c.email,
      'cuentaVerificada': c.cuentaVerificada
    }
  }
}

const clienteAPI = new ClienteAPI();

export default clienteAPI;
