import React, { Component } from 'react';
import { TextField } from '@material-ui/core';
import NavbarUsuario from "./NavbarUsuario.js";
import ProductosAPI from "../services/ProductAPI";
import { Redirect } from "react-router-dom";
import navegacion from "../utils/navegacion";
import './styles.css';

class ProductoCrear extends Component {

    constructor(props) {
        super(props);
        this.state = {
          comprado: false,  
          nombre: '',
          descripcion: '',
        };
    }

    myChangeHandler = (event) => {
        let nam = event.target.name;
        let val = event.target.value;
        this.setState({[nam]: val});
    }

    mySubmitHandler = (event) => {
        event.preventDefault();
        // sacar el harcodeo de negocio
        ProductosAPI.crearProducto(1, this.state);
        this.setState({comprado: true});
    }

    _form(){
        return(
            <form noValidate onSubmit={this.mySubmitHandler} className="productoCrearForm">
                <TextField id="standard-basic" 
                    name="nombre"
                    type="text" 
                    label="Nombre" 
                    onChange={this.myChangeHandler}
                />
                <TextField id="standard-basic" 
                    name="descripcion"
                    type="text" 
                    label="Descripción" 
                    onChange={this.myChangeHandler}
                />
                <input type="submit" value="Crear"/>
            </form>
        );
    }
    
    render() {
        if (this.state.comprado) {
            return <Redirect to={navegacion.getProductos()} />
        }
        return (
            <div>
                <NavbarUsuario />
                <div className="productoCrear">
                    <h1>Crear Nuevo Producto</h1>
                    {this._form()}
                </div>
            </div>
        );
    }
}

export default ProductoCrear;