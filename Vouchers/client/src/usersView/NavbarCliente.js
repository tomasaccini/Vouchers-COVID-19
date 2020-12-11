import React from "react";
// material-ui components
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import StorefrontIcon from '@material-ui/icons/Storefront';
import InboxIcon from '@material-ui/icons/Inbox';


export default function NavbarCliente() {

    const redireccionarAComprar = () => {
        window.location.replace('/clientes/comprar');
    };
    
    const redireccionarACanjear = () => {
        window.location.replace('/clientes/canjear');
    };
    
    const redireccionarAPerfil = () => {
        window.location.replace('/clientes/perfil');
    };

  return (
    <div>
        <ListItem button key={"Comprar Vouchers"} onClick={redireccionarAComprar}>
            <ListItemIcon><ShoppingCartIcon/></ListItemIcon>
            <ListItemText primary={"Comprar Vouchers"} />
        </ListItem>
        <ListItem button key={"Canjear mis Vouchers"} onClick={redireccionarACanjear}>
            <ListItemIcon><StorefrontIcon/></ListItemIcon>
            <ListItemText primary={"Canjear mis Vouchers"} />
        </ListItem>
        <ListItem button key={"Mi Perfil"} onClick={redireccionarAPerfil}>
            <ListItemIcon> <InboxIcon /> </ListItemIcon>
            <ListItemText primary={"Mi Perfil"} />
        </ListItem>
    </div>
  );
}