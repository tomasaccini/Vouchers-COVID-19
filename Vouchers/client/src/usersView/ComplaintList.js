import React, {Component} from 'react';
import GridContainer from "../components/Grid/GridContainer.js";
import GridItem from "../components/Grid/GridItem.js";
import ComplaintCard from './ComplaintCard.js';

class ComplaintList extends Component {

    render() {
        return (
            <GridContainer spacing={3}>
                {this.props.complaints.map((complaint) =>
                    <GridItem xs={12}>
                        <ComplaintCard/>
                    </GridItem>
                )}
            </GridContainer>
        );
    }
}

export default ComplaintList;