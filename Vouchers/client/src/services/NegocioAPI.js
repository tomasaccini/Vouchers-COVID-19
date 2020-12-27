import {SERVER_URL} from '../config';
import { format } from 'date-fns';

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
    const talonarios = negocio.talonariosCommands.map(t => this._transformarTalonario(t, negocio));

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

  _transformarTalonario(talonario, negocio) {

    console.log('talonario', talonario)

    const iv = talonario.informacionVoucherCommand;
    console.log('iv', iv)

    const desde = new Date(iv.validoDesde);
    const hasta = new Date(iv.validoHasta);

    return {
      'Titulo': 'Medialunas Veganas',
      'Descripcion': iv.descripcion,
      'Precio': iv.precio,
      'ValidoDesde': format(desde, 'yyyy/MM/dd'),
      'ValidoHasta': format(hasta, 'yyyy/MM/dd'),
      'Stock': talonario.stock,
      // TODO no more owner !!!!
      'isOwned': false,
      'NombreNegocio': negocio.name
    }
  }

}

const negocioAPI = new NegocioAPI();

export default negocioAPI;
