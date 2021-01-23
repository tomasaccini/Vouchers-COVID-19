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
import NavbarNegocio from './NavbarNegocio';
import NavbarCliente from './NavbarCliente';

import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
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


export default function NavbarUsuario(props) {
  const [open, setOpen] = React.useState(false);

  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const classes = useStyles();

  const cerrarSesion = () => {
    localStorage.setItem('userId', -1);
    localStorage.setItem('tipoUsuario', '');
    window.location.replace(navegacion.getIniciarSesionUrl());
  }

  return (
    <div className="root">
      <AppBar position="fixed">
        <Toolbar variant="dense">
          <IconButton id="navbarOpciones" edge="start" className="menuButton" color="inherit" aria-label="menu" onClick={handleClick}>
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" className={classes.title}>
            {props.title}
          </Typography>
          <Button id="cerrarSesionButton" className="signOutButton" simple onClick={cerrarSesion}>
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
              {
                localStorage.getItem('tipoUsuario') === 'cliente' ?
                  <NavbarCliente /> : <NavbarNegocio />
              }
              <ListItem button key={constantes.cerrarSesionTitulo} onClick={cerrarSesion}>
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
