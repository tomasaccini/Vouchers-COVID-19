import React from "react";
import { withStyles } from "@material-ui/core/styles";
import Avatar from "@material-ui/core/Avatar";
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";
import { Typography } from "@material-ui/core";
import VerifiedUserIcon from '@material-ui/icons/VerifiedUser';
import NotInterestedIcon from '@material-ui/icons/NotInterested';

const drawerWidth = 240;

const styles = theme => ({
    drawerHeader: {
        display: "flex",
        alignItems: "center",
        padding: theme.spacing(0, 1),
        ...theme.mixins.toolbar,
        justifyContent: "flex-end",
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing(3),
        transition: theme.transitions.create("margin", {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
        marginLeft: 0,
    },
    contentShift: {
        transition: theme.transitions.create("margin", {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
        }),
        marginLeft: drawerWidth,
    },
    form: {
        marginTop: theme.spacing(1),
        marginLeft: theme.spacing(40),
        marginRight: theme.spacing(40),
        padding: `0px ${theme.spacing(4)}px`,
    },
    avatar: {
        margin: "auto",
        width: theme.spacing(15),
        height: theme.spacing(15),
        fontSize: theme.spacing(7),
    },
    avatarContainer: {
        top: "25%",
    },
    property: {
        marginLeft: theme.spacing(2),
        padding: theme.spacing(3),
    },
    paperContainer: {
        width: "100%",
        height: "100%",
    },
    rightButton: {
        display: "flex",
        marginLeft: "auto",
        marginRight: theme.spacing(2),
        marginBottom: theme.spacing(2),
    },
});

class ClientePerfilVer extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        const { perfil, classes } = this.props;
        return (
            <div>
                <Grid container spacing={8}>
                    <Grid item xs={4}>
                        <Grid align="center" justify="center" direction="column" container spacing={2}>
                            <Grid item>
                                <Avatar src={null} className={classes.avatar}>
                                    {perfil.nombreCompleto.split(' ')[0][0]}
                                    {perfil.nombreCompleto.split(' ')[1][0]}
                                </Avatar>
                            </Grid>
                            <Grid item>
                                <Typography style={{ fontStyle: "italic" }} className={classes.property} variant="h6">
                                    {`Usuario: ${perfil.nombreCompleto}`}
                                </Typography>
                            </Grid>
                        </Grid>
                    </Grid>
                    <Grid item xs={8}>
                        <Paper className={classes.paperContainer}>
                            <Typography className={classes.property} variant="h6">{`Nombre: ${perfil.nombreCompleto.split(' ')[0]}`}</Typography>
                            <Typography className={classes.property} variant="h6">{`Apellido: ${perfil.nombreCompleto.split(' ')[1]}`}</Typography>
                            <Typography className={classes.property} variant="h6">{`Email: ${perfil.email}`}</Typography>
                            <Typography className={classes.property} variant="h6">{`Telefono: ${perfil.numeroTelefono}`}</Typography>
                            <Typography className={classes.property} variant="h6">Cuenta Verificada:
                                {perfil.cuentaVerificada ? <VerifiedUserIcon /> : <NotInterestedIcon />}
                            </Typography>
                        </Paper>
                    </Grid>
                </Grid>
            </div>
        );
    }
}

export default withStyles(styles)(ClientePerfilVer);