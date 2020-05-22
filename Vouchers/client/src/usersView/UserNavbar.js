import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import Drawer from '@material-ui/core/Drawer';
import Typography from '@material-ui/core/Typography';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import StorefrontIcon from '@material-ui/icons/Storefront';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Button from "components/CustomButtons/Button.js";

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

  const redirectToBuy = () => {
    window.location.replace('/users/comprar');
  };

  const redirectToExchange = () => {
    window.location.replace('/users/canjear');
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
              <ListItem button key={"Comprar Vouchers"} onClick={redirectToBuy}>
                <ListItemIcon><ShoppingCartIcon/></ListItemIcon>
                <ListItemText primary={"Comprar Vouchers"} />
              </ListItem>
              <ListItem button key={"Canjear mis Vouchers"} onClick={redirectToExchange}>
                <ListItemIcon><StorefrontIcon/></ListItemIcon>
                <ListItemText primary={"Canjear mis Vouchers"} />
              </ListItem>
            </List>
          </div>
        </Drawer>
      </AppBar>
    </div>
  );
}
