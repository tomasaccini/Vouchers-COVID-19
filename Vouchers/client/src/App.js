import React, { Component } from 'react';
import HomePage from "./usersView/Home";
import ClienteComprarVouchersPage from "./usersView/ClienteComprarVouchers";
import ClientesCanjearVouchersPage from "./usersView/ClienteCanjearVouchers";
import NegocioPerfil from "./usersView/NegocioPerfil"
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
            localStorage.setItem('userId', '-1');
            localStorage.setItem('tipoUsuario', '');
        }
    }

    inicioSesion() {
        const usuarioId = localStorage.getItem('userId')
        return usuarioId !== '-1';
    }

    validarSesionInciadaCliente(componente) {
        if (localStorage.getItem('tipoUsuario') === 'cliente') {
            return componente;
        }
        return <Redirect to={navegacion.getIniciarSesionUrl()} />
    }

    validarSesionInciadaNegocio(props, renderComponente) {
        if (localStorage.getItem('tipoUsuario') === 'negocio') {
            return renderComponente();
        }
        return <Redirect to={navegacion.getIniciarSesionUrl()} />
    }

    validarSesionNoIniciada(componente) {
        const usuarioId = localStorage.getItem('userId')
        const tipoUsuario = localStorage.getItem('tipoUsuario')

        if (tipoUsuario === '') {
            return componente;
        } else if (tipoUsuario === 'cliente') {
            return <Redirect to={navegacion.getClientePerfilUrl()} />
        }
        return <Redirect to={navegacion.getNegocioPerfilUrl(usuarioId)} />
    }

    validarSesionIniciada(props, renderComponente) {
        console.log('!!!! props', props)
        const negocioId = props.match ? props.match.params.negocioId : '';

        if (this.inicioSesion()) {
            return renderComponente({ negocioId });
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
                    <Route
                      path={`${navegacion.getNegocioPerfilUrl('')}:negocioId`}
                      render={props => this.validarSesionIniciada(
                        props,
                        ({ negocioId }) => <NegocioPerfil adsadsa={'adssada'} negocioId={negocioId} />)}
                    />
                    <Route path={navegacion.getClientePerfilUrl()}>
                        {this.validarSesionInciadaCliente(<ClientePerfilPage />)}
                    </Route>
                    <Route path={navegacion.getClienteComprarVoucherUrl()}>
                        {this.validarSesionInciadaCliente(<ClienteComprarVouchersPage />)}
                    </Route>
                    <Route path={navegacion.getClienteCanjearVoucherUrl()}>
                        {this.validarSesionInciadaCliente(<ClientesCanjearVouchersPage />)}
                    </Route>
                    <Route
                      path={navegacion.getReclamos()}
                      render={props => this.validarSesionIniciada(
                        props,
                        () => <Reclamos usuarioId={parseInt(localStorage.getItem('userId'))}/>)}
                    />
                    <Route
                      path={navegacion.getTalonariosCrear()}
                      render={props => this.validarSesionInciadaNegocio(
                        props,
                        () => <TalonariosCrearPage />)}
                    />
                    <Route
                      path={navegacion.getTalonarios()}
                      render={props => this.validarSesionInciadaNegocio(
                        props,
                        () => <TalonariosPage />)}
                    />
                    <Route
                      path={navegacion.getProductosCrear()}
                      render={props => this.validarSesionInciadaNegocio(
                        props,
                        () => <ProductosCrearPage />)}
                    />
                    <Route
                      path={navegacion.getProductos()}
                      render={props => this.validarSesionInciadaNegocio(
                        props,
                        () => <ProductosPage />)}
                    />
                    <Route path="/">
                        <HomePage />
                    </Route>
                </Switch>
            </Router>
        );
    }
}

export default App;
