import {SERVER_URL} from '../config';


class ClientAPI {

  async getClient(clientId){
    const url = `${SERVER_URL}clients/${clientId}`;
    const res = await fetch(url);
    const client = await res.json();
    return this._transformClient(client)
  }

  _transformClient(c) {

    return {
      'fullName': c.fullName,
      'phoneNumber': c.phoneNumber,
      'email': c.email
    }
  }

}

const clientAPI = new ClientAPI();

export default clientAPI;