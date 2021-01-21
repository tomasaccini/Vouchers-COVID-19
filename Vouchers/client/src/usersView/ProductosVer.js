import React, {Component} from 'react';

import NavbarUsuario from "./NavbarUsuario.js";
import ProductosList from "./productos/ProductosList";
import productoAPI from "../services/ProductAPI";
import navegacion from '../utils/navegacion';
import { Link } from "react-router-dom";
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
        return await productoAPI.getProductos(this.props.negocioId);
    }

    render() {
        return (
            <div>
                <NavbarUsuario />
                <div className="productosVista">
                    <div className="tituloProductos">
                        <h1>Mis Productos</h1>
                        <Link to={navegacion.getProductosCrearUrl()} >
                            <Button id="crearNuevoProductoButton" color="primary" size="large">
                                + Crear nuevo
                            </Button>
                        </Link>
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
