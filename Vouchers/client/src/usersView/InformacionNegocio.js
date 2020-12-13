import React, {Component} from 'react';
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";

class InformacionNegocio extends Component {

    // Complete real info
    render() {
        return (
            <GridContainer spacing={3} className="informacionNegocioGrid">
                <GridItem xs={4} className="businessDescription">
                    {this.props.info.descripcion}
                </GridItem>
            </GridContainer>
        );
    }
}

export default InformacionNegocio;