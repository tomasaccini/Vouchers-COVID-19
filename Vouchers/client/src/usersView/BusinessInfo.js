import React, {Component} from 'react';
import GridContainer from "../components/Grid/GridContainer.js";
import GridItem from "../components/Grid/GridItem.js";

class BusinessInfo extends Component {

    // Complete real info
    render() {
        return (
            <GridContainer spacing={3} className="businessInfoGrid">
                <GridItem xs={4} className="businessDescription">
                    Somos una panaderia que hace cosas con mucho amor. Con tu ayuda
                    vamos a poder hacer más medialunas
                </GridItem>
            </GridContainer>
        );
    }
}

export default BusinessInfo;