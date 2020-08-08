import React from "react";
// material-ui components
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import StorefrontIcon from '@material-ui/icons/Storefront';
import InboxIcon from '@material-ui/icons/Inbox';


export default function NavbarClient() {

    const redirectToBuy = () => {
        window.location.replace('/users/comprar');
    };
    
    const redirectToExchange = () => {
        window.location.replace('/users/canjear');
    };
    
    const redirectToProfile = () => {
        window.location.replace('/users/profile');
    };

  return (
    <div>
        <ListItem button key={"Comprar Vouchers"} onClick={redirectToBuy}>
            <ListItemIcon><ShoppingCartIcon/></ListItemIcon>
            <ListItemText primary={"Comprar Vouchers"} />
        </ListItem>
        <ListItem button key={"Canjear mis Vouchers"} onClick={redirectToExchange}>
            <ListItemIcon><StorefrontIcon/></ListItemIcon>
            <ListItemText primary={"Canjear mis Vouchers"} />
        </ListItem>
        <ListItem button key={"Mi Perfil"} onClick={redirectToProfile}>
            <ListItemIcon> <InboxIcon /> </ListItemIcon>
            <ListItemText primary={"Mi Perfil"} />
        </ListItem>
    </div>
  );
}