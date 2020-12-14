import React, {Component} from 'react';
import constantes from "../utils/constantes";
import NavbarUsuario from "./NavbarUsuario.js";

class Tarifarios extends Component {

    render() {
        return (
            <div>
                <NavbarUsuario title={constantes.misTarifariosTitulo} />
            </div>
        );
    }
}

export default Tarifarios;
