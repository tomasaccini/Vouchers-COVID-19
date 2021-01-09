import React from 'react';
// material-ui components
import { makeStyles } from '@material-ui/core/styles';
// core components
import Card from 'components/Card/Card.js';
import CardBody from 'components/Card/CardBody.js';
import CardHeader from 'components/Card/CardHeader.js';
import CardFooter from 'components/Card/CardFooter.js';
import '../styles.css';

import { cardTitle } from 'assets/jss/material-kit-react.js';
import ReclamarVoucherButton from "../ReclamarVoucherButton";

const styles = {
    cardTitle,
    textMuted: {
      color: '#6c757d'
    }
  };

const useStyles = makeStyles(styles);


export default function TarjetaVoucherCanjeada(props) {
    const classes = useStyles();
  
    const tipoUsuario = localStorage.getItem('tipoUsuario');
  
    const tarjetaHeader = tipoUsuario === 'negocio' ? props.data.clienteEmail : props.data.negocioNombre;
  
  
    return (
      <Card className="voucherCanjeado">
        <CardHeader color="info"><b>{tarjetaHeader}</b></CardHeader>
        <CardBody>
          <h2 className={classes.cardTitle}>{props.data.titulo}</h2>
          <p>
            {props.data.descripcion}
          </p>
        </CardBody>
        <div style={{'margin': '0 20px', 'display': 'flex', 'justifyContent': 'space-between'}}>
          <CardFooter className={classes.textMuted}>
            Retirar antes del {props.data.validoHasta}
          </CardFooter>
          <div style={{'visibility': 'hidden'}}>
            <ReclamarVoucherButton voucherId={props.data.id} />
          </div>
        </div>
      </Card>
    );
  }
