import {SERVER_URL} from '../config';


class BusinessAPI {

  async getBusiness(businessId){
    const url = `${SERVER_URL}negocios/${businessId}`;
    const res = await fetch(url);
    const business = await res.json();
    return this._transformBusiness(business)
  }

  _transformBusiness(b) {
    const counterfoils = b.counterfoils._embedded.map((c) => this._transformCounterfoil(c, b));

    return {
      'name': b.name,
      'category': b.category,
      'description': "Somos una panaderia que hace cosas con mucho amor. Con tu ayuda vamos a poder hacer más medialunas",
      'website': b.website,
      'phone_number': b.phone_number,
      'address': {
        'calle': b.address.calle,
        'provincia': b.address.provincia,
        'pais': b.address.pais,
        'numero': b.address.numero,
        'departamento': b.address.departamento
      },
      'counterfoils': counterfoils
    }
  }

  _transformCounterfoil(counterfoil, business) {

    const iv = counterfoil._embedded.informacionVoucher;

    return {
      'Titulo': 'Medialunas Veganas',
      'Descripcion': iv.descripcion,
      'Precio': iv.precio,
      // TODO format dates from 2020-08-01T03:00:00Z to 2020-08-01 !!!!
      'ValidoDesde': iv.validoDesde,
      'ValidoHasta': iv.validoHasta,
      'Stock': counterfoil.stock,
      // TODO no more owner !!!!
      'tieneDuenio': false,
      'NombreNegocio': business.name
    }
  }

}

const businessAPI = new BusinessAPI();

export default businessAPI;