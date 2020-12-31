import React from "react";
// material-ui components
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import FeedbackIcon from '@material-ui/icons/Feedback';

import ListItemText from '@material-ui/core/ListItemText';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import StorefrontIcon from '@material-ui/icons/Storefront';
import InboxIcon from '@material-ui/icons/Inbox';
import HistoryIcon from '@material-ui/icons/History';
import navegacion from '../utils/navegacion';
import constantes from "../utils/constantes";


export default function NavbarCliente() {

    const redirigirAComprarVouchers = () => {
        window.location.replace(navegacion.getClienteComprarVoucherUrl());
    };

    const redirigirACanjearVouchers = () => {
        window.location.replace(navegacion.getClienteCanjearVoucherUrl());
    };

    const redirigirAMiPerfil = () => {
        window.location.replace(navegacion.getClientePerfilUrl());
    };

    const redirigirAReclamos = () => {
        window.location.replace(navegacion.getReclamosUrl());
    };

    const redirigirAHistorialVouchers = () => {
        window.location.replace(navegacion.getClienteHistorialVoucherUrl());
    };

    return (
        <div>
            <ListItem button key={constantes.miPerfilTitulo} onClick={redirigirAMiPerfil}>
                <ListItemIcon> <InboxIcon /> </ListItemIcon>
                <ListItemText primary={constantes.miPerfilTitulo} />
            </ListItem>
            <ListItem button key={constantes.comprarVouchersTitulo} onClick={redirigirAComprarVouchers}>
                <ListItemIcon> <ShoppingCartIcon /> </ListItemIcon>
                <ListItemText primary={constantes.comprarVouchersTitulo} />
            </ListItem>
            <ListItem button key={constantes.canjearVouchersTitulo} onClick={redirigirACanjearVouchers}>
                <ListItemIcon> <StorefrontIcon /> </ListItemIcon>
                <ListItemText primary={constantes.canjearVouchersTitulo} />
            </ListItem>
            <ListItem button key={constantes.historialVouchersTitulo} onClick={redirigirAHistorialVouchers}>
                <ListItemIcon> <HistoryIcon /> </ListItemIcon>
                <ListItemText primary={constantes.historialVouchersTitulo} />
            </ListItem>
            <ListItem button key={constantes.reclamosTitulo} onClick={redirigirAReclamos}>
                <ListItemIcon> <FeedbackIcon /> </ListItemIcon>
                <ListItemText primary={constantes.reclamosTitulo} />
            </ListItem>
        </div>
    );
}
