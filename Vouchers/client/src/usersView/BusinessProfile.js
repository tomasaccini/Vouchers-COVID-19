import React, {Component} from 'react';

import UserNavbar from "./UserNavbar.js";
import GridContainer from "components/Grid/GridContainer.js";
import "./styles.css";


class BusinessProfile extends Component{

    constructor() {
        super();
        this.state = { profile: {} };
    }

    async componentDidMount() {
        const profile = await this.getBusinessProfile(null);
        this.setState({ profile })
    }

    async getBusinessProfile() {
        //TODO: Bring profile from VoucherApi
        const mockProfile = {"name": "Costumbres Argentinas"}
        return mockProfile
    }

    render() {
        return (
            <div>
                <UserNavbar title="Profile" />
                <GridContainer className="vouchersGrid">
                    <h1>{this.state.profile.name}</h1>
                </GridContainer>
            </div>
        );
    }

}

export default BusinessProfile