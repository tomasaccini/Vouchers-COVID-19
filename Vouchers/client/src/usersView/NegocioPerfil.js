import React, {Component} from 'react';

import UserNavbar from "./UserNavbar.js";
import MapSection from "./Map.js"
import GridContainer from "components/Grid/GridContainer";
import ListaVouchers from "./ListaVouchers.js";
import InformacionNegocio from "./InformacionNegocio.js";
import GridItem from "components/Grid/GridItem";
import "./styles.css";
import { Typography } from '@material-ui/core';
import negocioAPI from '../services/NegocioAPI.js';
import constantes from "../utils/constantes";


class NegocioPerfil extends Component{

    constructor() {
        super();
        this.location = {
            "address": 'Costumbres Argentinas',
            "lat": -34.599373,
            "lng": -58.438230,
          } // here until i learn react properly
        this.state = { perfil: {}};
    }

    async getPerfilNegocio(id) {
        return await negocioAPI.getNegocio(id);
    }

    async componentDidMount() {
        const perfil = await this.getPerfilNegocio(1);
        this.setState({ perfil: perfil })
        console.log("DEBUG: ", this.state.perfil);
    }

    render() {
        return (
            <div>
                <UserNavbar title={constantes.miNegocioTitulo} />
                <GridContainer className="businessProfileGrid">
                    <GridItem xs={12}>
                        <h1 class="businessTitle">{this.state.perfil.nombre}</h1>
                    </GridItem>
                    <GridItem xs={12}>
                        <MapSection location={this.location} zoomLevel={17} />
                    </GridItem>
                    <GridItem xs={12}>
                        <InformacionNegocio info={this.state.perfil}/>
                    </GridItem>
                    <GridItem xs={12}>
                        <Typography component="h1" variant="h5" className="businessSubTitle">
                            Vouchers disponibles para comprar
                        </Typography>
                    </GridItem>
                    <GridItem>
                        {this.state.perfil.tarifarios ? 
                            <ListaVouchers vouchers={this.state.perfil.tarifarios}/> : 
                            <h1>Loading</h1> }
                            {/* TODO: Create loading component */}
                    </GridItem>
                </GridContainer>
            </div>
        );
    }

}

export default NegocioPerfil