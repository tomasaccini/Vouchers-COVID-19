import React, {Component} from 'react';
import GridContainer from "../components/Grid/GridContainer.js";
import GridItem from "../components/Grid/GridItem.js";
import VoucherCard from "./VoucherCard.js"

class VouchersList extends Component {

    render() {
        return (
            <GridContainer spacing={3}>
                {this.props.vouchers.map((voucher) =>
                    <GridItem xs={4}>
                        <VoucherCard data={voucher}/>
                    </GridItem>
                )}
            </GridContainer>
        );
    }
}

export default VouchersList;
