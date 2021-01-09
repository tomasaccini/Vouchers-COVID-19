import React, {Component} from 'react';

import NavbarUsuario from "./NavbarUsuario.js";
import voucherAPI from "../services/VoucherAPI";
import TarjetaVoucherCanjeada from "./tarjetas/TarjetaVoucherCanjeada"
import "./styles.css";
import constantes from "../utils/constantes";

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

    cambiarVista(){
        this.setState({ canjeados: !this.state.canjeados });
    }

    render() {
        return (
            <div>
                <NavbarUsuario title={constantes.historialVouchersTitulo} />
                <section className="historialCliente">
                    <div className="compradosExpiradoBtns">
                        <button onClick={() => this.cambiarVista()} className={`historialBtn ${this.state.canjeados === true ? "historialBtnSelected" : ""}`}>
                            Canjeados
                        </button>
                        <button onClick={() => this.cambiarVista()} className={`historialBtn ${this.state.canjeados === false ? "historialBtnSelected" : ""}`}>
                            Expirados
                        </button>
                    </div>
                    <section className={`vouchersListHistorial ${this.state.canjeados === true ? "showSection" : "hideSection"}`}>
                        {this.state.vouchersCanjeados.map((voucher) =>
                            <TarjetaVoucherCanjeada data={voucher} /> 
                        )}
                    </section>
                    <section className={`vouchersListHistorial ${this.state.canjeados === false ? "showSection" : "hideSection"}`}>
                        {this.state.vouchersExpirados.map((voucher) =>
                            <TarjetaVoucherCanjeada data={voucher} /> 
                        )}
                    </section>
                </section>
            </div>
        );
    }
}

export default ClienteHistorialVouchers;
