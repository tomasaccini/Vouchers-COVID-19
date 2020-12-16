import {SERVER_URL} from '../config';

class ProductAPI {
    async getProductos(negocioId) {
        const url = `${SERVER_URL}productos/obtenerPorNegocio/${negocioId}`;
        console.log(`debug | getProductos URL is: ${url}`);
        const res = await fetch(url);
        const productos = await res.json();
        console.log(`debug | getProductos: `, productos);
        return productos.map((p) => this._transformarProducto(p));
    }

    _transformarProducto(producto) {
        return {
          'id': producto.id,
          'titulo': producto.nombre,
          'descripcion': producto.descripcion
        }
      }
}

const productAPI = new ProductAPI();

export default productAPI;