import React, {Component} from 'react';

import UserNavbar from "./UserNavbar.js";
import GridContainer from "components/Grid/GridContainer";
import GridItem from "components/Grid/GridItem";
import "./styles.css";
import clientAPI from '../services/ClientAPI.js';
import constantes from "../utils/constantes";


class ClientePerfil extends Component{

    constructor() {
        super();
        this.state = { profile: {}};
    }

    async getClientProfile(id) {
        return await clientAPI.getClient(id);
    }

    async componentDidMount() {
        const profile = await this.getClientProfile(2);
        this.setState({ profile: profile })
    }

    render() {
        return (
            <div>
                <UserNavbar title={constantes.miPerfilTitulo} />
                <GridContainer className="businessProfileGrid">
                    <GridItem xs={12}>
                        <h1 class="clientTitle">{this.state.profile.fullName}</h1>
                    </GridItem>
                    <GridItem xs={12}>
                        Telefono: {this.state.profile.phoneNumber}
                    </GridItem>
                    <GridItem xs={12}>
                        Mail: {this.state.profile.mail}
                    </GridItem>
                </GridContainer>
            </div>
        );
    }

}

export default ClientePerfil