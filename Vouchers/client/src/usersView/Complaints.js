import React, {Component} from 'react';

import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import UserNavbar from "./UserNavbar.js";
import "./styles.css";
import ComplaintList from './ComplaintList.js';

class Complaints extends Component {

    constructor() {
        super();
        this.state = { complaints: [] };
    }

    async componentDidMount() {
        const complaints = await this.getListOfComplaints();
        this.setState({ complaints })
    }

    async getListOfComplaints() {
        //return await complaintAPI.getComplaints(null);
        return [{description: "Queja"}]
    }

    render() {
        return (
            <div>
                <UserNavbar title="Quejas" />
                <GridContainer className="vouchersGrid">
                    <GridItem>
                        <ComplaintList complaints={this.state.complaints}/>
                    </GridItem>
                </GridContainer>
            </div>
        );
    }
}

export default Complaints;
