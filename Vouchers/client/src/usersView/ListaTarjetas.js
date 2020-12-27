import React, {Component} from 'react';
import GridContainer from "../components/Grid/GridContainer.js";
import GridItem from "../components/Grid/GridItem.js";
import TarjetaTalonario from "./tarjetas/TarjetaTalonario.js"
import TarjetaVoucher from "./tarjetas/TarjetaVoucher.js"
import voucherHelper from "../utils/voucherHelper";

class ListaTarjetas extends Component {

    render() {
        return (
            <GridContainer spacing={3}>
                {this.props.vouchers.map((voucher) =>
                    <GridItem xs={4}>
                        { voucherHelper.esVoucher(voucher)
                          ? <TarjetaVoucher data={voucher} />
                          : <TarjetaTalonario data={voucher} />
                        }
                    </GridItem>
                )}
            </GridContainer>
        );
    }
}

export default ListaTarjetas;
