import React from "react";
// material-ui components
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import FeedbackIcon from '@material-ui/icons/Feedback';
import ListItemText from '@material-ui/core/ListItemText';
import InboxIcon from '@material-ui/icons/Inbox';
import navegacion from '../utils/navegacion';
import constantes from "../utils/constantes";


export default function NavbarNegocio() {

  const redirigirAMiNegocio = () => {
    window.location.replace(navegacion.getNegocioPerfilUrl());
  };

  const redirigirAReclamos = () => {
    window.location.replace(navegacion.getReclamos());
  };

  return (
    <div>
      <ListItem button key={constantes.miNegocioTitulo} onClick={redirigirAMiNegocio}>
        <ListItemIcon> <InboxIcon /> </ListItemIcon>
        <ListItemText primary={constantes.miNegocioTitulo} />
      </ListItem>
      <ListItem button key={constantes.reclamosTitulo} onClick={redirigirAReclamos}>
        <ListItemIcon> <FeedbackIcon /> </ListItemIcon>
        <ListItemText primary={constantes.reclamosTitulo} />
      </ListItem>
    </div>
  );
}
