import React from "react";
// material-ui components
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import InboxIcon from '@material-ui/icons/Inbox';


export default function NavbarNegocio() {
    
    const redireccionarAPerfil = () => {
        window.location.replace('/negocios/perfil');
    };

  return (
    <div>
        <ListItem button key={"Mi Negocio"} onClick={redireccionarAPerfil}>
            <ListItemIcon> <InboxIcon /> </ListItemIcon>
            <ListItemText primary={"Mi Negocio"} />
        </ListItem>
    </div>
  );
}
