import React, {Component} from 'react';

import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import ListaVouchers from "./ListaVouchers.js";
import NavbarUsuario from "./NavbarUsuario.js";
import voucherAPI from "../services/VoucherAPI";
import "./styles.css";
import constantes from "../utils/constantes";
import SearchBar from "../components/SearchBar";

class ClienteComprarVouchers extends Component {

    constructor() {
        super();
        this.state = { 
            talonarios: [] , 
            inputNegocio: '',
            inputVoucher: ''};
    }

    async componentDidMount() {
        const talonarios = await this.getListaDeTalonarios();

        this.setState({ talonarios })
    }

    async getListaDeTalonarios() {
        return await voucherAPI.getTalonarios(localStorage.getItem('userId'));
    }

    async search(q){
        // TODO: implement
        console.log("buscando: " + q);
        // const talonarios = await this.getListaDeTalonariosFilter();
        // this.setState({ talonarios })
    }

    render() {
        return (
            <div>
                <NavbarUsuario title={constantes.comprarVouchersTitulo} />
                <section className="buyVoucher">
                    <div className="searchBars">
                        <SearchBar 
                            nombre = {"Voucher"}
                            input = {this.state.inputVoucher} 
                            onChange = {this.search}
                        />
                        <SearchBar 
                            nombre = {"Negocio"}
                            input = {this.state.inputNegocio} 
                            onChange = {this.search}
                        />
                    </div>
                    <GridContainer className="vouchersGrid">
                        <GridItem>
                            <ListaVouchers vouchers={this.state.talonarios}/>
                        </GridItem>
                    </GridContainer>
                </section>
            </div>
        );
    }
}

export default ClienteComprarVouchers;
