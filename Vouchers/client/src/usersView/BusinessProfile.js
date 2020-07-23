import React, {Component} from 'react';

import UserNavbar from "./UserNavbar.js";
import MapSection from "./Map.js"
import GridContainer from "components/Grid/GridContainer.js";
import VouchersList from "./VouchersList.js";
import BusinessInfo from "./BusinessInfo.js";
import voucherAPI from "../services/VoucherAPI";
import GridItem from "components/Grid/GridItem.js";
import "./styles.css";
import { Typography } from '@material-ui/core';


class BusinessProfile extends Component{

    constructor() {
        super();
        this.location = {
            "address": 'Costumbres Argentinas',
            "lat": -34.599373,
            "lng": -58.438230,
          } // here until i learn react properly
        this.state = { profile: {}, counterfoils: []};
    }

    async componentDidMount() {
        const profile = await this.getBusinessProfile(null);
        const counterfoils = await this.getListOfVouchers(null);
        // Todo: must get business counterfoils
        this.setState({ profile: profile, counterfoils: counterfoils })
    }

    async getListOfVouchers(id) {
        return await voucherAPI.getCounterfoils(null);
    }

    async getBusinessProfile(id) {
        //TODO: Bring profile from VoucherApi
        const location = {
            "address": '1600 Amphitheatre Parkway, Mountain View, california.',
            "lat": 37.42216,
            "lng": -122.08427,
          }
        const mockProfile = {"name": "Costumbres Argentinas",
                             "location": location}
        return mockProfile
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
                        <VouchersList vouchers={this.state.counterfoils}/>
                    </GridItem>
                </GridContainer>
            </div>
        );
    }

}

export default BusinessProfile