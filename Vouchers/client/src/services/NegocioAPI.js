import {SERVER_URL} from '../config';
import { format } from 'date-fns';
import voucherAPI from "./VoucherAPI";

class NegocioAPI {



  async getNegocio(negocioId){
    const url = `${SERVER_URL}/negocios/${negocioId}`;
    const res = await fetch(url);

    if (res.status !== 200) {
      return null;
    }

    const negocio = await res.json();
    return this._transformarNegocio(negocio)
  }

  _transformarNegocio(negocio) {
    console.log('!!!! voucherAPI', voucherAPI)
    console.log('!!!! negocio', negocio)
    const talonarios = negocio.talonariosCommands.map(t => voucherAPI._transformarTalonario(t));

    return {
      'nombre': negocio.nombre,
      'categoria': negocio.categoria,
      'descripcion': "Somos una panaderia que hace cosas con mucho amor. Con tu ayuda vamos a poder hacer más medialunas",
      'paginaWeb': negocio.website,
      'telefono': negocio.numeroTelefonico,
      'direccion': {
        'calle': negocio.direccion.calle,
        'provincia': negocio.direccion.provincia,
        'pais': negocio.direccion.pais,
        'numero': negocio.direccion.numero,
        'departamento': negocio.direccion.departamento
      },
      'talonarios': talonarios
    }
  }
}

const negocioAPI = new NegocioAPI();

export default negocioAPI;
