import React, {Component} from 'react';

import UserNavbar from "./UserNavbar.js";
import MapSection from "./Map.js"
import GridContainer from "components/Grid/GridContainer.js";
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
        this.state = { profile: {} };
    }

    async componentDidMount() {
        const profile = await this.getBusinessProfile(null);
        this.setState({ profile })
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
                    <GridItem xs={12} className="gridItemm">
                        <Typography component="h1" variant="h5">
                            {this.state.profile.name}
                        </Typography>
                    </GridItem>
                    <GridItem xs={12} backgroundColor="green" className="gridItemm">
                        <MapSection location={this.location} zoomLevel={17} />
                    </GridItem>
                </GridContainer>
            </div>
        );
    }

}

export default BusinessProfile