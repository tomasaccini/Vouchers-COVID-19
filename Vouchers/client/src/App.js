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
import ClientePerfilPage from './usersView/ClientePerfil';
import Reclamos from "./usersView/reclamos/Reclamos";

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
                        <Reclamos />
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
