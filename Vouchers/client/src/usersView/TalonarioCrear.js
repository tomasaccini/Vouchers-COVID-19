import React, { Component } from 'react';
import { TextField } from '@material-ui/core';
import NavbarUsuario from "./NavbarUsuario.js";
import TalonarioAPI from "../services/TalonarioAPI";
import ProductoAPI from "../services/ProductAPI";
import { Redirect } from "react-router-dom";
import navegacion from "../utils/navegacion";
import './styles.css';

class TalonarioCrear extends Component {

    constructor(props) {
        super(props);
        this.state = {
          creado: false,
          descripcion: '',
          stock: 0,
          precio: 0,
          validoDesde: '',
          validoHasta: '',
          productos: []
        };
    }

    async componentDidMount() {
        const productos = await this.getListaDeProductos();
        this.setState({ productos });
    }

    async getListaDeProductos() {
        var productos = await ProductoAPI.getProductos(1);
        return productos.map((p) => {return {'nombre': p.titulo, 'cantidad': 0, 'id': p.id};});
    }

    myChangeHandler = (event) => {
        let nam = event.target.name;
        let val = event.target.value;
        this.setState({[nam]: val});
    }

    myChangeHandlerProduct = (event) => {
        let id = parseInt(event.target.name.slice(1));
        var nProductos = this.state.productos.map((p) => {
            if (p.id === id){
                return {'nombre': p.nombre, 'cantidad': parseInt(event.target.value), 'id': p.id};
            }
            return p;
        });
        this.setState({ productos : nProductos });
    }

    mySubmitHandler = (event) => {
        event.preventDefault();
        // sacar el harcodeo de negocio
        console.log(this.state.productos)
        TalonarioAPI.crearTalonario(1, this.state);
        this.setState({creado: true});
    }

    _form(){
        return(
            <form noValidate onSubmit={this.mySubmitHandler} className="talonarioCrearForm">
                <TextField id="descripcionTalonario" 
                    className="talonarioField"
                    name="descripcion"
                    type="text" 
                    label="Descripción" 
                    onChange={this.myChangeHandler}
                />
                <TextField id="stockTalonario" 
                    className="talonarioField"
                    name="stock"
                    type="number" 
                    label="Stock" 
                    onChange={this.myChangeHandler}
                />
                <TextField id="precioTalonario" 
                    className="talonarioField"
                    name="precio"
                    type="number" 
                    label="Precio" 
                    onChange={this.myChangeHandler}
                />
                <TextField id="desdeTalonario" 
                    className="talonarioField"
                    name="validoDesde"
                    type="date" 
                    label="Valido desde" 
                    onChange={this.myChangeHandler}
                />
                <TextField id="hastaTalonario" 
                    className="talonarioField"
                    name="validoHasta"
                    type="date" 
                    label="Valido hasta" 
                    onChange={this.myChangeHandler}
                />
                <h2>Productos a Agregar:</h2>
                {
                    this.state.productos.map( producto =>
                        <li className="productoElegir">
                            <h3>{producto.nombre}</h3>
                            <TextField id={producto.id}
                                name={"p" + producto.id.toString()}
                                type="text" 
                                label="cantidad" 
                                onChange={this.myChangeHandlerProduct}
                            />
                        </li>
                    )
                }
                <input type="submit" value="Crear"/>
            </form>
        );
    }
    
    render() {
        if (this.state.creado) {
            return <Redirect to={navegacion.getTalonariosUrl()} />
        }
        return (
            <div>
                <NavbarUsuario />
                <div className="talonarioCrear">
                    <h1>Crear Nuevo Talonario</h1>
                    {this._form()}
                </div>
            </div>
        );
    }
}

export default TalonarioCrear;
