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
        const vouchersCompradosSinReclamoAbierto = vouchers.filter(voucher => voucher.state === 'Comprado' && !voucher.reclamoAbierto);
        const vouchersCompradosConReclamoAbierto = vouchers.filter(voucher => voucher.state === 'Comprado' && voucher.reclamoAbierto);
        const vouchersConfirmacionPendiente = vouchers.filter(voucher => voucher.state === 'ConfirmacionPendiente');

        const vouchersOrdenados = vouchersCompradosSinReclamoAbierto.concat(vouchersConfirmacionPendiente).concat(vouchersCompradosConReclamoAbierto);
        return vouchersOrdenados;
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
