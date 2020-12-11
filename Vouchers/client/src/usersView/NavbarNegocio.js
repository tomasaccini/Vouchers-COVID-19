import React from "react";
// material-ui components
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import InboxIcon from '@material-ui/icons/Inbox';
import navegacion from '../utils/navegacion';
import constantes from "../utils/constantes";


export default function NavbarNegocio() {

  const redirigirAMiNegocio = () => {
    window.location.replace(navegacion.getNegocioPerfilUrl());
  };

  return (
    <div>
      <ListItem button key={constantes.miNegocioTitulo} onClick={redirigirAMiNegocio}>
        <ListItemIcon> <InboxIcon /> </ListItemIcon>
        <ListItemText primary={constantes.miNegocioTitulo} />
      </ListItem>
    </div>
  );
}
