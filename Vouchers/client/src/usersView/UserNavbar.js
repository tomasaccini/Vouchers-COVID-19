import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import Drawer from '@material-ui/core/Drawer';
import Typography from '@material-ui/core/Typography';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import List from '@material-ui/core/List';
import Button from "../components/CustomButtons/Button.js";
import NavbarBusiness from './NavbarBusiness';
import NavbarClient from './NavbarClient';

import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import ErrorOutlineIcon from '@material-ui/icons/ErrorOutline';

import FeedbackIcon from '@material-ui/icons/Feedback';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import StorefrontIcon from '@material-ui/icons/Storefront';
import InboxIcon from '@material-ui/icons/Inbox';
import navegacion from '../utils/navegacion';
import constantes from "../utils/constantes";

const drawerWidth = 240;

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    color: "white",
    flexGrow: 1,
    textAlign: "left"
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  toolbar: theme.mixins.toolbar,
  drawerHeader: {
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
  },
  signOutButton: {
    color: 'white',
  },
  bottomContainer: {
    flex: 1,
    justifyContent: 'flex-end',
  }
}));


export default function DenseAppBar(props) {
  const [open, setOpen] = React.useState(false);

  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const redirectToComplaints = () => {
    window.location.replace('/complaints');
  }

  const redirigirAComprarVouchers = () => {
    window.location.replace(navegacion.getClienteComprarVoucherUrl());
  };

  const redirigirACanjearVouchers = () => {
    window.location.replace(navegacion.getClienteCanjearVoucherUrl());
  };

  const redirigirAMiPerfil = () => {
    window.location.replace(navegacion.getNegocioPerfilUrl());
  };

  const redirigirAReclamos = () => {
    window.location.replace(navegacion.getReclamos());
  };

  const classes = useStyles();

  return (
    <div className="root">
      <AppBar position="fixed">
        <Toolbar variant="dense">
          <IconButton edge="start" className="menuButton" color="inherit" aria-label="menu" onClick={handleClick}>
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" className={classes.title}>
            {props.title}
          </Typography>
          <Button className="signOutButton" simple>
            Cerrar sesión
          </Button>
        </Toolbar>
        <Drawer
          className={classes.drawer}
          classes={{
            paper: classes.drawerPaper,
          }}
          open={open}
          keepMounted
          onClose={handleClose}
        >
          <div className={classes.toolbar}>
            <div className={classes.drawerHeader}>
              <IconButton onClick={handleClose}>
                <ChevronLeftIcon />
              </IconButton>
            </div>
            <List>
              {/* True debe ser reemplazado por el verdadero chequeo de usario business/client */}
              { true ? 
                <NavbarBusiness></NavbarBusiness> : 
                <NavbarClient></NavbarClient>
              }
              <ListItem button key={constantes.miPerfilTitulo} onClick={redirigirAMiPerfil}>
                <ListItemIcon> <InboxIcon /> </ListItemIcon>
                <ListItemText primary={constantes.miPerfilTitulo} />
              </ListItem>
              <ListItem button key={constantes.comprarVouchersTitulo} onClick={redirigirAComprarVouchers}>
                <ListItemIcon> <ShoppingCartIcon/> </ListItemIcon>
                <ListItemText primary={constantes.comprarVouchersTitulo} />
              </ListItem>
              <ListItem button key={constantes.canjearVouchersTitulo} onClick={redirigirACanjearVouchers}>
                <ListItemIcon> <StorefrontIcon/> </ListItemIcon>
                <ListItemText primary={constantes.canjearVouchersTitulo} />
              </ListItem>
              <ListItem button key={constantes.reclamosTitulo} onClick={redirigirAReclamos}>
                <ListItemIcon> <FeedbackIcon /> </ListItemIcon>
                <ListItemText primary={constantes.reclamosTitulo} />
              </ListItem>
              <ListItem button key={constantes.cerrarSesionTitulo}>
                <ListItemIcon> <ExitToAppIcon /> </ListItemIcon>
                <ListItemText primary={constantes.cerrarSesionTitulo} />
              </ListItem>
            </List>
          </div>
        </Drawer>
      </AppBar>
    </div>
  );
}
