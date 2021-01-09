import React, {Component} from 'react';

import NavbarUsuario from "./NavbarUsuario.js";
import voucherAPI from "../services/VoucherAPI";
import TarjetaVoucherCanjeado from "./tarjetas/TarjetaVoucherCanjeado"
import "./styles.css";
import constantes from "../utils/constantes";
import ListaTarjetas from "./ListaTarjetas";
import GridContainer from "../components/Grid/GridContainer";
import GridItem from "../components/Grid/GridItem";

class ClienteHistorialVouchers extends Component {

    constructor() {
        super();
        this.state = { 
            vouchersCanjeados: [],
            vouchersExpirados: [],
            canjeados: true
        };
    }

    async componentDidMount() {
        const vouchers = await this.getListaDeVouchers(constantes.canjeado);
        const vouchersCanjeados = vouchers.filter(v => v.state === 'Canjeado');
        const vouchersExpirados = vouchers.filter(v => v.state === 'Expirado');

        this.setState({ vouchersCanjeados: vouchersCanjeados, vouchersExpirados: vouchersExpirados})
    }

    async getListaDeVouchers(estado) {
        const vouchers = await voucherAPI.getVouchersConEstado(localStorage.getItem('userId'), estado);
        return vouchers
    }

    cambiarVista(cambiarACanjeados){
        if (cambiarACanjeados && this.state.canjeados) {
            return;
        }

        if (!cambiarACanjeados && !this.state.canjeados) {
            return;
        }

        console.log('!!!! canjeados', this.state.canjeados)
        console.log('!!!! cambiarACanjeados', cambiarACanjeados)

        this.setState({ canjeados: cambiarACanjeados });
    }

    render() {
        return (
            <div>
                <NavbarUsuario title={constantes.historialVouchersTitulo} />
                <section className="historialCliente">
                    <div className="compradosExpiradoBtns">
                        <button onClick={() => this.cambiarVista(true)} className={`historialBtn ${this.state.canjeados === true ? "historialBtnSelected" : ""}`}>
                            Canjeados
                        </button>
                        <button onClick={() => this.cambiarVista(false)} className={`historialBtn ${this.state.canjeados === false ? "historialBtnSelected" : ""}`}>
                            Expirados
                        </button>
                    </div>
                </section>
                <GridContainer className="vouchersGrid">
                    <GridItem>
                        <ListaTarjetas vouchers={this.state.canjeados === true ? this.state.vouchersCanjeados : []} />
                    </GridItem>
                    <GridItem>
                        <ListaTarjetas vouchers={this.state.canjeados === false ? this.state.vouchersExpirados : []} />
                    </GridItem>
                </GridContainer>
            </div>
        );
    }
}

export default ClienteHistorialVouchers;
