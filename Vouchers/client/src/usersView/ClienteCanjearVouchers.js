import React, {Component} from 'react';
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import ListaVouchers from "./ListaVouchers.js";
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
        console.log('!!!!', vouchers)

        this.setState({ vouchers })
    }

    async getListaDeVouchers() {
        return await voucherAPI.getVouchers(localStorage.getItem('userId'));
    }


    render() {
        return (
            <div>
                <NavbarUsuario title={constantes.canjearVouchersTitulo} />
                <GridContainer className="vouchersGrid">
                    <GridItem>
                        <ListaVouchers vouchers={this.state.vouchers}/>
                    </GridItem>
                </GridContainer>
            </div>
        );
    }
}

export default ClienteCanjearVouchers;
