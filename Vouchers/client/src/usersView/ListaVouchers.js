import React, {Component} from 'react';
import GridContainer from "../components/Grid/GridContainer.js";
import GridItem from "../components/Grid/GridItem.js";
import VoucherSinDuenio from "./VoucherSinDuenio.js"
import VoucherConDuenio from "./VoucherConDuenio.js"

class ListaVouchers extends Component {

    render() {
        const { habilitarCompra } = this.props;

        return (
            <GridContainer spacing={3}>
                {this.props.vouchers.map((voucher) =>
                    <GridItem xs={4}>
                        { voucher.isOwned
                          ? <VoucherConDuenio data={voucher} />
                          : <VoucherSinDuenio data={voucher} />
                        }
                    </GridItem>
                )}
            </GridContainer>
        );
    }
}

export default ListaVouchers;
