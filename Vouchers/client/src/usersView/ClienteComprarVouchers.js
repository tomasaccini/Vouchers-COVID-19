import React, {Component} from 'react';

import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import ListaVouchers from "./ListaVouchers.js";
import NavbarUsuario from "./NavbarUsuario.js";
import voucherAPI from "../services/VoucherAPI";
import "./styles.css";
import constantes from "../utils/constantes";

class ClienteComprarVouchers extends Component {

    constructor() {
        super();
        this.state = { talonarios: [] };
    }

    async componentDidMount() {
        const talonarios = await this.getListaDeTalonarios();

        this.setState({ talonarios })
    }

    async getListaDeTalonarios() {
        return await voucherAPI.getTalonarios(localStorage.getItem('userId'));
    }


    render() {
        return (
            <div>
                <NavbarUsuario title={constantes.comprarVouchersTitulo} />
                <GridContainer className="vouchersGrid">
                    <GridItem>
                        <ListaVouchers vouchers={this.state.talonarios}/>
                    </GridItem>
                </GridContainer>
            </div>
        );
    }
}

export default ClienteComprarVouchers;
