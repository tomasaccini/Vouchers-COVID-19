import React, {Component} from 'react';
import constantes from "../utils/constantes";
import NavbarUsuario from "./NavbarUsuario.js";
import navegacion from '../utils/navegacion';
import { Link } from "react-router-dom";
import Button from "../components/CustomButtons/Button.js";

import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import ListaTarjetas from "./ListaTarjetas.js";
import voucherAPI from "../services/VoucherAPI";

import './styles.css';

class VouchersConfirmables extends Component {

  constructor() {
    super();
    this.state = { vouchersConfirmables: [] };
  }

  async componentDidMount() {
    const vouchersConfirmables = await this.getVouchersConfirmables();

    this.setState({ vouchersConfirmables: vouchersConfirmables })
  }

  async getVouchersConfirmables() {
    const usuarioId = localStorage.getItem('userId')
    return await voucherAPI.getVouchersConfirmablesPorNegocio(usuarioId);
  }

  render() {
    return (
      <div>
        <NavbarUsuario title={constantes.vouchersConfirmables} />
        <div className="tituloProductos">
          <h1>Vouchers Confirmables</h1>
        </div>
        <GridContainer className="vouchersGrid">
          <GridItem>
            <ListaTarjetas vouchers={this.state.vouchersConfirmables}/>
          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

export default VouchersConfirmables;
