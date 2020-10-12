import React, {Component} from 'react';

import UserNavbar from '../UserNavbar.js';
import MapSection from '../Map.js'
import GridContainer from "components/Grid/GridContainer";
import VouchersList from '../VouchersList.js';
import BusinessInfo from '../BusinessInfo.js';
import GridItem from "components/Grid/GridItem";
import "../styles.css";
import { Typography } from '@material-ui/core';
import businessAPI from '../../services/BusinessAPI.js';


class Reclamos extends Component{

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
                <UserNavbar title="Reclamos" />
                <GridContainer className="businessProfileGrid">
                </GridContainer>
            </div>

        );
    }

}

export default Reclamos