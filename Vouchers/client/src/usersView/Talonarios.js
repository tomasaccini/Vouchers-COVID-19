import React, {Component} from 'react';
import constantes from "../utils/constantes";
import NavbarUsuario from "./NavbarUsuario.js";
import navegacion from '../utils/navegacion';
import { Link } from "react-router-dom";
import Button from "../components/CustomButtons/Button.js";

import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import ListaVouchers from "./ListaVouchers.js";
import voucherAPI from "../services/VoucherAPI";

import './styles.css';

class Talonarios extends Component {

    constructor() {
        super();
        this.state = { talonarios: [] };
    }

    async componentDidMount() {
        const talonarios = await this.getListaDeTalonarios();

        this.setState({ talonarios })
    }

    async getListaDeTalonarios() {
        return await voucherAPI.getTalonariosPorNegocio(1);
    }


    render() {
        return (
            <div>
                <NavbarUsuario title={constantes.misTalonariosTitulo} />
                <div className="tituloProductos">
                    <h1>Mis Talonarios</h1>
                    <Link to={navegacion.getTalonariosCrear()} >
                        <Button color="primary" size="large">
                            + Crear nuevo
                        </Button>
                    </Link>
                </div>
                <GridContainer className="vouchersGrid">
                    <GridItem>
                        <ListaVouchers vouchers={this.state.talonarios}/>
                    </GridItem>
                </GridContainer>
            </div>
        );
    }
}

export default Talonarios;
