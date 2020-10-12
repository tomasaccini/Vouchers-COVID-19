import React, {Component} from 'react';

import UserNavbar from "./UserNavbar.js";
import MapSection from "./Map.js"
import GridContainer from "components/Grid/GridContainer";
import VouchersList from "./VouchersList.js";
import BusinessInfo from "./BusinessInfo.js";
import GridItem from "components/Grid/GridItem";
import "./styles.css";
import { Typography } from '@material-ui/core';
import businessAPI from '../services/BusinessAPI.js';


class NegocioPerfil extends Component{

    constructor() {
        super();
        this.location = {
            "address": 'Costumbres Argentinas',
            "lat": -34.599373,
            "lng": -58.438230,
          } // here until i learn react properly
        this.state = { profile: {}};
    }

    async getBusinessProfile(id) {
        return await businessAPI.getBusiness(id);
    }

    async componentDidMount() {
        const profile = await this.getBusinessProfile(1);
        this.setState({ profile: profile })
    }

    render() {
        return (
            <div>
                <UserNavbar title="Profile" />
                <GridContainer className="businessProfileGrid">
                    <GridItem xs={12}>
                        <h1 class="businessTitle">{this.state.profile.name}</h1>
                    </GridItem>
                    <GridItem xs={12}>
                        <MapSection location={this.location} zoomLevel={17} />
                    </GridItem>
                    <GridItem xs={12}>
                        <BusinessInfo info={this.state.profile}/>
                    </GridItem>
                    <GridItem xs={12}>
                        <Typography component="h1" variant="h5" className="businessSubTitle">
                            Vouchers disponibles para comprar
                        </Typography>
                    </GridItem>
                    <GridItem>
                        {this.state.profile.counterfoils ? 
                            <VouchersList vouchers={this.state.profile.counterfoils}/> : 
                            <h1>Loading</h1> }
                            {/* TODO: Create loading component */}
                    </GridItem>
                </GridContainer>
            </div>
        );
    }

}

export default NegocioPerfil