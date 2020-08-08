import React from "react";
// material-ui components
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import InboxIcon from '@material-ui/icons/Inbox';


export default function NavbarBusiness() {
    
    const redirectToProfile = () => {
        window.location.replace('/business/profile');
    };

  return (
    <div>
        <ListItem button key={"Mi Perfil"} onClick={redirectToProfile}>
            <ListItemIcon> <InboxIcon /> </ListItemIcon>
            <ListItemText primary={"Mi Perfil"} />
        </ListItem>
    </div>
  );
}
