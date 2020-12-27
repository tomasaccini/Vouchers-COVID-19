import React from "react";
// material-ui components
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import FeedbackIcon from '@material-ui/icons/Feedback';
import ListItemText from '@material-ui/core/ListItemText';
import InboxIcon from '@material-ui/icons/Inbox';
import CreateIcon from '@material-ui/icons/AddCircle';
import ThumbUp from "@material-ui/icons/ThumbUp";
import navegacion from '../utils/navegacion';
import constantes from "../utils/constantes";


export default function NavbarNegocio() {

  const usuarioId = localStorage.getItem('userId');

  const redirigirAMiNegocio = () => {
    window.location.replace(navegacion.getNegocioPerfilUrl(usuarioId));
  };

  const redirigirAVouchersConfirmables = () => {
    window.location.replace(navegacion.getVouchersConfirmablesUrl());
  };

  const redirigirACrearTalonarios = () => {
    window.location.replace(navegacion.getTalonariosUrl());
  };

  const redirigirAProductos = () => {
    window.location.replace(navegacion.getProductosUrl());
  };

  const redirigirAReclamos = () => {
    window.location.replace(navegacion.getReclamosUrl());
  };

  return (
    <div>
      <ListItem button key={constantes.miNegocioTitulo} onClick={redirigirAMiNegocio}>
        <ListItemIcon> <InboxIcon /> </ListItemIcon>
        <ListItemText primary={constantes.miNegocioTitulo} />
      </ListItem>
      <ListItem button key={constantes.vouchersConfirmables} onClick={redirigirAVouchersConfirmables}>
        <ListItemIcon> <ThumbUp /> </ListItemIcon>
        <ListItemText primary={constantes.vouchersConfirmables} />
      </ListItem>
      <ListItem button key={constantes.misTalonariosTitulo} onClick={redirigirACrearTalonarios}>
        <ListItemIcon> <CreateIcon /> </ListItemIcon>
        <ListItemText primary={constantes.misTalonariosTitulo} />
      </ListItem>
      <ListItem button key={constantes.misProductosTitulo} onClick={redirigirAProductos}>
        <ListItemIcon> <CreateIcon /> </ListItemIcon>
        <ListItemText primary={constantes.misProductosTitulo} />
      </ListItem>
      <ListItem button key={constantes.reclamosTitulo} onClick={redirigirAReclamos}>
        <ListItemIcon> <FeedbackIcon /> </ListItemIcon>
        <ListItemText primary={constantes.reclamosTitulo} />
      </ListItem>
    </div>
  );
}
