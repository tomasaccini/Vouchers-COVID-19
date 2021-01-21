import React, {Component} from 'react';
import constantes from "../utils/constantes";
import NavbarUsuario from "./NavbarUsuario.js";
import navegacion from '../utils/navegacion';
import { Link } from "react-router-dom";
import Button from "../components/CustomButtons/Button.js";

import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import ListaTarjetas from "./ListaTarjetas.js";
import voucherAPI from "../services/VoucherAPI";

import './styles.css';

class Talonarios extends Component {

    constructor() {
        super();
        this.state = { 
            talonariosActivos: [],
            talonariosPausados: [],
            verActivos: true };
    }

    async componentDidMount() {
        const talonarios = await this.getListaDeTalonarios();
        const talonariosActivos = talonarios.filter(t => t.activo === true);
        const talonariosPausados = talonarios.filter(t => t.activo === false);
        this.setState({ talonariosActivos: talonariosActivos, talonariosPausados: talonariosPausados })
    }

    async getListaDeTalonarios() {
        return await voucherAPI.getTalonariosPorNegocio(this.props.negocioId);
    }

    cambiarVista(cambiaraActivos){
        if (cambiaraActivos && this.state.verActivos) {
            return;
        }

        if (!cambiaraActivos && !this.state.verActivos) {
            return;
        }

        this.setState({ verActivos: cambiaraActivos });
    }

    render() {
        return (
            <div>
                <NavbarUsuario title={constantes.misTalonariosTitulo} />
                <div className="tituloTalonarios">
                    <h1>Mis Talonarios</h1>
                    <Link to={navegacion.getTalonariosCrearUrl()} >
                        <Button id="crearNuevoTalonarioButton" color="primary" size="large">
                            + Crear nuevo
                        </Button>
                    </Link>
                </div>
                <section className="talonariosEstadoNegocio">
                    <div className="activosNoActivosBtns">
                        <button id="TalonariosActivosTab" onClick={() => this.cambiarVista(true)} className={`historialBtn ${this.state.verActivos === true ? "historialBtnSelected" : ""}`}>
                            Activos
                        </button>
                        <button id="TalonariosNoActivosTab" onClick={() => this.cambiarVista(false)} className={`historialBtn ${this.state.verActivos === false ? "historialBtnSelected" : ""}`}>
                            No Activos
                        </button>
                    </div>
                </section>
                <GridContainer className="vouchersGrid">
                    <GridItem>
                        <ListaTarjetas vouchers={this.state.verActivos === true ? this.state.talonariosActivos : []} />
                    </GridItem>
                    <GridItem>
                        <ListaTarjetas vouchers={this.state.verActivos === false ? this.state.talonariosPausados : []} />
                    </GridItem>
                </GridContainer>
            </div>
        );
    }
}

export default Talonarios;
