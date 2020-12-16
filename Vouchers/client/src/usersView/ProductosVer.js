import React, {Component} from 'react';

import NavbarUsuario from "./NavbarUsuario.js";
import ProductosList from "./productos/ProductosList";
import productoAPI from "../services/ProductAPI";
import Button from "../components/CustomButtons/Button.js";

import './styles.css';

class ProductosVer extends Component {

    constructor() {
        super();
        this.state = { productos: []};
    }

    async componentDidMount() {
        const productos = await this.getListaDeProductos();

        this.setState({ productos })
    }

    async getListaDeProductos() {
        return await productoAPI.getProductos(localStorage.getItem('userId'));
    }

    render() {
        return (
            <div>
                <NavbarUsuario />
                <div className="productosVista">
                    <div className="tituloProductos">
                        <h1>Mis Productos</h1>
                        <Button color="primary" size="large" onClick={() => console.log('d')}>
                            + Crear nuevo
                        </Button>
                    </div>
                    <div className="productosDisplay">
                        <ProductosList productos={this.state.productos} />
                    </div>
                </div>
            </div>
        );
    }
}

export default ProductosVer;