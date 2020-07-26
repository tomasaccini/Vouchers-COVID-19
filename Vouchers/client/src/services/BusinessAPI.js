import {SERVER_URL} from '../config';


class BusinessAPI {

  async getBusiness(businessId){
    const url = `${SERVER_URL}businesses/${businessId}`;
    const response = await fetch(url);
    const business = await response.json();
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
        'street': b.address.street,
        'province': b.address.province,
        'country': b.address._embedded.country.name,
        'number': b.address.number,
        'apartment': b.address.apartment
      },
      'counterfoils': counterfoils
    }
  }

  _transformCounterfoil(counterfoil, business) {

    const vi = counterfoil._embedded.voucherInformation

    return {
      'Title': 'Medialunas Veganas',
      'Description': vi.description,
      'Price': vi.price,
      // TODO format dates from 2020-08-01T03:00:00Z to 2020-08-01 !!!!
      'CreationDate': vi.validFrom,
      'EndDate': vi.validUntil,
      'Stock': counterfoil.stock,
      // TODO no more owner !!!!
      'isOwned': false,
      'shopName': business.name
    }
  }

}

const businessAPI = new BusinessAPI();

export default businessAPI;