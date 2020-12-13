import React, { Component } from 'react';
import { withStyles } from "@material-ui/core/styles";
import NavbarUsuario from "./NavbarUsuario.js";
import ClientePerfilVer from "./ClientePerfilVer.js";
import "./styles.css";
import clienteAPI from '../services/ClienteAPI.js';
import constantes from "../utils/constantes";
import navegacion from '../utils/navegacion';

const drawerWidth = 240;

const styles = theme => ({
  drawerHeader: {
    display: "flex",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
    justifyContent: "flex-end",
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3),
    transition: theme.transitions.create("margin", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    marginLeft: 0,
  },
  contentShift: {
    transition: theme.transitions.create("margin", {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
    marginLeft: drawerWidth,
  },
  form: {
    marginTop: theme.spacing(1),
    marginLeft: theme.spacing(40),
    marginRight: theme.spacing(40),
    padding: `0px ${theme.spacing(4)}px`,
  },
});

class ClientePerfil extends Component {

  constructor() {
    super();
    this.state = { perfil: {} };
  }

  async getPerfilCliente(id) {
    return await clienteAPI.getCliente(id);
  }

  async componentDidMount() {
    const perfil = await this.getPerfilCliente(localStorage.getItem('userId'));
    this.setState({ perfil: perfil });
  }

  render() {
    const { classes } = this.props;
    return (
      <div>
        <NavbarUsuario title={constantes.miPerfilTitulo} />
        <div className={classes.drawerHeader} />
        <div className={classes.form}>
          {this.state.perfil.nombreCompleto ? (
            <ClientePerfilVer perfil={this.state.perfil} />
          ) : (
              <div>No hay datos del usuario</div>
            )}
        </div>
      </div>
    );
  }

}

export default withStyles(styles)(ClientePerfil);