import React, {Component} from 'react';
import GridContainer from "../components/Grid/GridContainer.js";
import GridItem from "../components/Grid/GridItem.js";
import TarjetaTalonario from "./tarjetas/TarjetaTalonario.js"
import TarjetaVoucher from "./tarjetas/TarjetaVoucher.js"
import voucherHelper from "../utils/voucherHelper";

class ListaTarjetas extends Component {

    definirSiEsTalonarioOVoucher(voucher) {
        if (voucherHelper.esVoucher(voucher)) {
          return <TarjetaVoucher data={voucher} />;
        } else {
            return <TarjetaTalonario data={voucher} />;
        }
    }

    render() {
        return (
            <GridContainer spacing={3}>
                {this.props.vouchers.map((voucher) =>
                    <GridItem xs={4}>
                        { this.definirSiEsTalonarioOVoucher(voucher) }
                    </GridItem>
                )}
            </GridContainer>
        );
    }
}

export default ListaTarjetas;
