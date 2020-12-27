import React, {Component} from 'react';
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import ListaTarjetas from "./ListaTarjetas.js";
import NavbarUsuario from "./NavbarUsuario.js";
import "./styles.css";
import constantes from '../utils/constantes'
import voucherAPI from "../services/VoucherAPI";

class ClienteCanjearVouchers extends Component {

    constructor() {
        super();
        this.state = { vouchers: [] };
    }

    async componentDidMount() {
        const vouchers = await this.getListaDeVouchers();

        this.setState({ vouchers })
    }

    async getListaDeVouchers() {
        const vouchers = await voucherAPI.getVouchers(localStorage.getItem('userId'));
        return vouchers.filter(voucher => !voucher.enReclamo);
    }

    render() {
        return (
            <div className="canjearVouchers">
                <NavbarUsuario title={constantes.canjearVouchersTitulo} />
                <GridContainer className="vouchersGrid">
                    <GridItem>
                        <ListaTarjetas vouchers={this.state.vouchers}/>
                    </GridItem>
                </GridContainer>
            </div>
        );
    }
}

export default ClienteCanjearVouchers;
