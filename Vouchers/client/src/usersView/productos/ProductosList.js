import React, {Component} from 'react';
import Producto from "./Producto";
import '../styles.css';

class ProductosList extends Component {
    render() {
        return (
            <div className="listadoProductos">
                {this.props.productos.map((producto) =>
                    <div>
                        <Producto data={producto}/>
                    </div>
                )}
            </div>
        );
    }
}

export default ProductosList;