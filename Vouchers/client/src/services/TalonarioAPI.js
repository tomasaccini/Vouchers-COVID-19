import {SERVER_URL} from '../config';
import VoucherAPI from './VoucherAPI';

class TalonarioAPI{

    async crearTalonario(negocioId, talonario) {
        const url = `${SERVER_URL}/talonarios`;
        console.log(`debug | crearTalonario URL is: ${url}`);
        const res = await fetch(url, {
          method: 'POST',
          body: JSON.stringify(
            { negocioId: negocioId, 
              stock: parseInt(talonario.stock),
              informacionVoucher: {
                descripcion: talonario.descripcion,
                validoDesde: talonario.validoDesde,
                validoHasta: talonario.validoHasta,
                precio: parseInt(talonario.precio)
              },
              productos: talonario.productos.filter(p => p.cantidad > 0)
            })
        });
        const nuevoTalonario = await res.json();
        console.log(`debug | creo Talonario: `, nuevoTalonario);
        return VoucherAPI._transformarTalonario(nuevoTalonario);
    }
    
}


const talonarioAPI = new TalonarioAPI();

export default talonarioAPI;
