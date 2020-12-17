import React, {Component} from 'react';
import constantes from "../utils/constantes";
import NavbarUsuario from "./NavbarUsuario.js";
import navegacion from '../utils/navegacion';
import { Link } from "react-router-dom";
import Button from "../components/CustomButtons/Button.js";

import './styles.css';

class Tarifarios extends Component {

    render() {
        return (
            <div>
                <NavbarUsuario title={constantes.misTarifariosTitulo} />
                <div className="tituloProductos">
                    <h1>Mis Tarifarios</h1>
                    <Link to={navegacion.getTarifariosCrear()} >
                        <Button color="primary" size="large">
                            + Crear nuevo
                        </Button>
                    </Link>
                </div>
            </div>
        );
    }
}

export default Tarifarios;
