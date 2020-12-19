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

    async crearProducto(negocioId, producto) {
      const url = `${SERVER_URL}productos/crear`;
      console.log(`debug | crearProducto URL is: ${url}`);
      const res = await fetch(url, {
        method: 'POST',
        body: JSON.stringify(
          { negocioId: negocioId, 
            nombre: producto.nombre,
            descripcion: producto.descripcion
          })
      });
      const nuevoProducto = await res.json();
      console.log(`debug | creo Producto: `, nuevoProducto);
      return this._transformarProducto(nuevoProducto);
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