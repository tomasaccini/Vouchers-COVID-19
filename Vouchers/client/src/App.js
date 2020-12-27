import React, { Component } from 'react';
import HomePage from "./usersView/Home";
import ClienteComprarVouchersPage from "./usersView/ClienteComprarVouchers";
import ClientesCanjearVouchersPage from "./usersView/ClienteCanjearVouchers";
import NegocioPerfilPage from "./usersView/NegocioPerfil"
import TalonariosPage from "./usersView/Talonarios"
import TalonariosCrearPage from "./usersView/TalonarioCrear"
import ProductosPage from "./usersView/ProductosVer"
import ProductosCrearPage from "./usersView/ProductoCrear"
import IniciarSesionPage from "./usersView/IniciarSesion";
import RegistrarsePage from "./usersView/Registrarse";
import OlvidoContraseniaPage from "./usersView/OlvidoContrasenia";
import navegacion from './utils/navegacion';

import {
    BrowserRouter as Router,
    Switch,
    Route,
    Redirect
} from "react-router-dom";
import ClientePerfilPage from './usersView/ClientePerfil';
import Reclamos from "./usersView/reclamos/Reclamos";

// TODO hardcodeado el usuarioId !!!!
class App extends Component {
    constructor(props) {
        super(props)

        if (!localStorage.hasOwnProperty('userId')) {
            localStorage.setItem('userId', -1);
            localStorage.setItem('tipoUsuario', '');
        }
    }

    validarSesionInciadaCliente(componente) {
        if (localStorage.getItem('tipoUsuario') === 'cliente') {
            return componente;
        }
        return <Redirect to={navegacion.getIniciarSesionUrl()} />
    }

    validarSesionInciadaNegocio(componente) {
        if (localStorage.getItem('tipoUsuario') === 'negocio') {
            return componente;
        }
        return <Redirect to={navegacion.getIniciarSesionUrl()} />
    }

    validarSesionNoIniciada(componente) {
        const tipoUsuario = localStorage.getItem('tipoUsuario')
        if (tipoUsuario === '') {
            return componente;
        } else if (tipoUsuario === 'cliente') {
            return <Redirect to={navegacion.getClientePerfilUrl()} />            
        }
        return <Redirect to={navegacion.getNegocioPerfilUrl()} />            
    }

    validarSesionIniciada(componente) {
        if (localStorage.getItem('tipoUsuario') !== '') {
            return componente;
        }
        return <Redirect to={navegacion.getIniciarSesionUrl()} />           
    }

    render() {
        return (
            <Router>
                <Switch>
                    <Route path={navegacion.getIniciarSesionUrl()}>
                        {this.validarSesionNoIniciada(<IniciarSesionPage />)}
                    </Route>
                    <Route path={navegacion.getRegistrarseUrl()}>
                        {this.validarSesionNoIniciada(<RegistrarsePage />)}
                    </Route>
                    <Route path={navegacion.getOlvidoContraseniaUrl()}>
                        {this.validarSesionNoIniciada(<OlvidoContraseniaPage />)}
                    </Route>
                    <Route path={navegacion.getNegocioPerfilUrl()}>
                        {this.validarSesionInciadaNegocio(<NegocioPerfilPage />)}
                    </Route>
                    <Route path={navegacion.getClientePerfilUrl()}>
                        {this.validarSesionInciadaCliente(<ClientePerfilPage />)}
                    </Route>
                    <Route path={navegacion.getClienteComprarVoucherUrl()}>
                        {this.validarSesionInciadaCliente(<ClienteComprarVouchersPage />)}
                    </Route>
                    <Route path={navegacion.getClienteCanjearVoucherUrl()}>
                        {this.validarSesionInciadaCliente(<ClientesCanjearVouchersPage />)}
                    </Route>
                    <Route path={navegacion.getReclamos()}>
                        {this.validarSesionIniciada(<Reclamos usuarioId={parseInt(localStorage.getItem('userId'))}/>)}
                    </Route>
                    <Route path={navegacion.getTalonariosCrear()}>
                        {this.validarSesionInciadaNegocio(<TalonariosCrearPage />)}
                    </Route>
                    <Route path={navegacion.getTalonarios()}>
                        {this.validarSesionInciadaNegocio(<TalonariosPage />)}
                    </Route>
                    <Route path={navegacion.getProductosCrear()}>
                        {this.validarSesionInciadaNegocio(<ProductosCrearPage />)}
                    </Route>
                    <Route path={navegacion.getProductos()}>
                        {this.validarSesionInciadaNegocio(<ProductosPage />)}
                    </Route>
                    <Route path="/">
                        <HomePage />
                    </Route>
                </Switch>
            </Router>
        );
    }
}

export default App;
