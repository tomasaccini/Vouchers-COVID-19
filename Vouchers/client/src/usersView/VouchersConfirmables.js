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
    this.state = { talonarios: [] };
  }

  async componentDidMount() {
    const talonarios = await this.getListaDeTalonarios();

    this.setState({ talonarios })
  }

  async getListaDeTalonarios() {
    return await voucherAPI.getTalonariosPorNegocio(1);
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
            <ListaTarjetas vouchers={this.state.talonarios}/>
          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

export default VouchersConfirmables;
