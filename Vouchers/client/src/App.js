import React, {Component} from 'react';
import GrailsApp from "./GrailsApp";
import ClienteComprarVouchersPage from "./usersView/ClienteComprarVouchers";
import ClientesCanjearVouchersPage from "./usersView/ClienteCanjearVouchers";
import NegocioPerfilPage from "./usersView/NegocioPerfil"
import IniciarSesionPage from "./usersView/SignInPage";
import RegistrarsePage from "./usersView/SignUpPage";
import OlvidoContraseniaPage from "./usersView/ForgotPasswordPage";
import navegacion from './utils/navegacion';

import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";
import Complaints from './usersView/Complaints';
import ClientePerfilPage from './usersView/ClientePerfil';
import Reclamos from "./usersView/reclamos/Reclamos";

// TODO hardcodeado el usuarioId !!!!
class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path={navegacion.geIniciarSesionUrl()}>
                        <IniciarSesionPage />
                    </Route>
                    <Route path={navegacion.getRegistrarseUrl()}>
                        <RegistrarsePage />
                    </Route>
                    <Route path={navegacion.getOlvidoContraseniaUrl()}>
                        <OlvidoContraseniaPage />
                    </Route>
                    <Route path={navegacion.getNegocioPerfilUrl()}>
                        <NegocioPerfilPage />
                    </Route>
                    <Route path={navegacion.getUsuarioPerfilUrl()}>
                        <ClientePerfilPage />
                    </Route>
                    <Route path={navegacion.getClienteComprarVoucherUrl()}>
                        <ClienteComprarVouchersPage />
                    </Route>
                    <Route path={navegacion.getClienteCanjearVoucherUrl()}>
                        <ClientesCanjearVouchersPage />
                    </Route>
                    <Route path={navegacion.getReclamos()}>
                        <Reclamos usuarioId={1} />
                    </Route>
                    <Route path="/complaints">
                        <Complaints/>
                    </Route>
                    <Route path="/">
                        <GrailsApp />
                    </Route>
                </Switch>
            </Router>
        );
    }
}

export default App;
