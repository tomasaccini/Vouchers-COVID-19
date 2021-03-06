import React, { Component } from 'react';

import NavbarUsuario from "./NavbarUsuario.js";
import MapSection from "./Map.js"
import GridContainer from "components/Grid/GridContainer";
import ListaTarjetas from "./ListaTarjetas.js";
import InformacionNegocio from "./InformacionNegocio.js";
import GridItem from "components/Grid/GridItem";
import "./styles.css";
import { Typography } from '@material-ui/core';
import negocioAPI from '../services/NegocioAPI.js';
import constantes from "../utils/constantes";
import navegacion from '../utils/navegacion';


class NegocioPerfil extends Component {

    constructor() {
        super();
        this.location = {
            "address": 'Costumbres Argentinas',
            "lat": -34.599373,
            "lng": -58.438230,
        } // here until i learn react properly
        this.state = { perfil: {} };
    }

    async getPerfilNegocio(id) {
        return await negocioAPI.getNegocio(id);
    }

    async componentDidMount() {
        const {negocioId} = this.props;

        const perfil = await this.getPerfilNegocio(negocioId);
        if (perfil === null) {
            window.alert('Error obteniendo negocio');
            window.location.replace(navegacion.getIniciarSesionUrl());
        }

        this.setState({perfil: perfil});
    }

    render() {
        return (
            <div>
                <NavbarUsuario title={constantes.miNegocioTitulo} />
                <GridContainer className="businessProfileGrid">
                    <GridItem xs={12}>
                        <h1 class="businessTitle">{this.state.perfil.nombre}</h1>
                    </GridItem>
                    <GridItem xs={12}>
                        <MapSection location={this.location} zoomLevel={17} />
                    </GridItem>
                    <GridItem xs={12}>
                        <InformacionNegocio info={this.state.perfil} />
                    </GridItem>
                    <GridItem xs={12}>
                        <Typography component="h1" variant="h5" className="businessSubTitle">
                            Vouchers disponibles para comprar
                        </Typography>
                    </GridItem>
                    <GridItem>
                        {this.state.perfil.talonarios ?
                            <ListaTarjetas vouchers={this.state.perfil.talonarios} /> :
                            <h1>Cargando</h1>}
                        {/* TODO: Create loading component */}
                    </GridItem>
                </GridContainer>
            </div>
        );
    }

}

export default NegocioPerfil
