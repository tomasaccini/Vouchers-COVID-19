import React, {useState} from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Paper from '@material-ui/core/Paper';
import Box from '@material-ui/core/Box';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import navegacion from '../utils/navegacion';

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
        Vouchers-Covid-19
      {' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

const useStyles = makeStyles((theme) => ({
  root: {
    height: '100vh',
  },
  image: {
    backgroundImage: 'url(https://source.unsplash.com/random)',
    backgroundRepeat: 'no-repeat',
    backgroundColor:
      theme.palette.type === 'light' ? theme.palette.grey[50] : theme.palette.grey[900],
    backgroundSize: 'cover',
    backgroundPosition: 'center',
  },
  paper: {
    margin: theme.spacing(8, 4),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

export default function IniciarSesion(props) {
  const classes = useStyles();

  const [datos, setDatos] = useState({
    email: '',
    contrasenia: ''
  })

  const iniciarSesionSubmit = (event) => {
    event.preventDefault()
    //TODO: integrate with backend
    console.log('submit! enviariamos datos...' + datos.email + ' ' + datos.contrasenia)

    if (datos.email.includes('negocio1')) {
      localStorage.setItem('tipoUsuario', 'negocio');
      localStorage.setItem('userId', 1);
      console.log(localStorage);
      const usuarioId = localStorage.getItem('userId');
      window.location.href = navegacion.getNegocioPerfilUrl(usuarioId);
      return;
    }

    if (datos.email.includes('negocio2')) {
      localStorage.setItem('tipoUsuario', 'negocio');
      localStorage.setItem('userId', 2);
      console.log(localStorage);
      const usuarioId = localStorage.getItem('userId');
      window.location.href = navegacion.getNegocioPerfilUrl(usuarioId);
      return;
    }

    if (datos.email.includes('cliente1')) {
      localStorage.setItem('tipoUsuario', 'cliente');
      localStorage.setItem('userId', 3);
      console.log(localStorage);
      window.location.href = navegacion.getClientePerfilUrl();
    }

    if (datos.email.includes('cliente2')) {
      localStorage.setItem('tipoUsuario', 'cliente');
      localStorage.setItem('userId', 4);
      console.log(localStorage);
      window.location.href = navegacion.getClientePerfilUrl();
      return;
    }
  }

  const handleInputChange = (event) => {
    setDatos({
      ...datos,
      [event.target.name]: event.target.value
    })
  }

  return (
    <Grid container component="main" className={classes.root}>
      <CssBaseline />
      <Grid item xs={false} sm={4} md={7} className={classes.image} />
      <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
        <div className={classes.paper}>
          <Avatar className={classes.avatar}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Iniciar Sesión
          </Typography>
          <form className={classes.form} noValidate onSubmit={iniciarSesionSubmit}>
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email"
              name="email"
              autoComplete="email"
              onChange={handleInputChange}
              autoFocus
            />
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="contrasenia"
              label="Contraseña"
              type="contrasenia"
              id="contrasenia"
              autoComplete="current-password"
              onChange={handleInputChange}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              className={classes.submit}
            >
              Iniciar Sesión
            </Button>
            <Grid container>
              <Grid item xs>
                <Link href="/olvidocontrasenia" variant="body2">
                  Olvidaste tu contraseña?
                </Link>
              </Grid>
              <Grid item>
                <Link href="/registrarse" variant="body2">
                  {"No tenes una cuenta? Registrate!"}
                </Link>
              </Grid>
            </Grid>
            <Box mt={5}>
              <Copyright />
            </Box>
          </form>
        </div>
      </Grid>
    </Grid>
  );
}
