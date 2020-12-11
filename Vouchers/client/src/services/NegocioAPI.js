import {SERVER_URL} from '../config';
import { format } from 'date-fns';

class NegocioAPI {

  async getNegocio(negocioId){
    const url = `${SERVER_URL}negocios/${negocioId}`;
    const res = await fetch(url);
    const negocio = await res.json();
    return this._transformarNegocio(negocio)
  }

  _transformarNegocio(n) {
    const tarifarios = n.counterfoils._embedded.map((t) => this._transformarTarifario(t, n));

    return {
      'nombre': n.name,
      'categoria': n.category,
      'descripcion': "Somos una panaderia que hace cosas con mucho amor. Con tu ayuda vamos a poder hacer más medialunas",
      'paginaWeb': n.website,
      'telefono': n.phone_number,
      'direccion': {
        'calle': n.address.calle,
        'provincia': n.address.provincia,
        'pais': n.address.pais,
        'numero': n.address.numero,
        'departamento': n.address.departamento
      },
      'tarifarios': tarifarios
    }
  }

  _transformarTarifario(tarifario, negocio) {

    const iv = tarifario._embedded.informacionVoucher;

    const desde = new Date(iv.validoDesde);
    const hasta = new Date(iv.validoHasta);

    return {
      'Titulo': 'Medialunas Veganas',
      'Descripcion': iv.descripcion,
      'Precio': iv.precio,
      'ValidoDesde': format(desde, 'yyyy/MM/dd'),
      'ValidoHasta': format(hasta, 'yyyy/MM/dd'),
      'Stock': tarifario.stock,
      // TODO no more owner !!!!
      'tieneDuenio': false,
      'NombreNegocio': negocio.name
    }
  }

}

const negocioAPI = new NegocioAPI();

export default negocioAPI;