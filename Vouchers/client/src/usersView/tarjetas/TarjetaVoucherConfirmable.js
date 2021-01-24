import React from 'react';
// material-ui components
import { makeStyles } from '@material-ui/core/styles';
import Slide from '@material-ui/core/Slide';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import DialogActions from '@material-ui/core/DialogActions';
import IconButton from '@material-ui/core/IconButton';
import Close from '@material-ui/icons/Close';
// core components
import Card from 'components/Card/Card.js';
import CardBody from 'components/Card/CardBody.js';
import CardHeader from 'components/Card/CardHeader.js';
import CardFooter from 'components/Card/CardFooter.js';
import Button from 'components/CustomButtons/Button.js';
import modalStyle from 'assets/jss/material-kit-react/modalStyle.js';

import { cardTitle } from 'assets/jss/material-kit-react.js';
import {Row} from "reactstrap";
import ReclamarVoucherButton from "../ReclamarVoucherButton";
import voucherAPI from "../../services/VoucherAPI";
import {Redirect} from "react-router-dom";
import navegacion from "../../utils/navegacion";

const styles = {
  cardTitle,
  textCenter: {
    textAlign: 'center'
  },
  textMuted: {
    color: '#6c757d'
  },
  ...modalStyle
};

const useStyles = makeStyles(styles);

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="down" ref={ref} {...props} />;
});

export default function TarjetaVoucherConfirmable(props) {
  const [modalConfirmar, setModalConfirmar] = React.useState(false);
  const [modalCancelar, setModalCancelar] = React.useState(false);
  const [actualizar, setActualizar] = React.useState(false);
  const classes = useStyles();

  const tipoUsuario = localStorage.getItem('tipoUsuario');

  const tarjetaHeader = tipoUsuario === 'negocio' ? props.data.clienteEmail : props.data.negocioNombre;


  const confirmarCanjeDeVoucher = async () => {
    const usuarioId = localStorage.getItem('userId');
    await voucherAPI.confirmarCanje(props.data.id, usuarioId);
    setActualizar(true);
  }

  const cancelarSolicitudDeCanje = async () => {
    const usuarioId = localStorage.getItem('userId');
    await voucherAPI.cancelarSolicitudDeCanje(props.data.id, usuarioId);
    setActualizar(true);
  }

  if (actualizar) {
    if (tipoUsuario === 'negocio') {
      window.location.replace(navegacion.getVouchersConfirmablesUrl());
    } else {
      window.location.replace(navegacion.getClienteCanjearVoucherUrl());
    }
  }

  return (
    <div>
      <Card className={classes.textCenter} style={{'min-height': '245px'}}>
        <CardHeader color="info">
          {tipoUsuario === 'negocio'
            ? <b>{tarjetaHeader}</b>
            : <a style={{'text-decoration': 'none', 'color': 'white',}}
                 href={navegacion.getNegocioPerfilUrl(props.data.negocioId)} target="_blank"
                 rel="noopener noreferrer"><b>{tarjetaHeader}</b></a>
          }
        </CardHeader>
        <CardBody>
          <h2 className={classes.cardTitle}>{props.data.titulo}</h2>
          <p style={{'font-size': '20px', 'font-weight': 'bold', 'align-self': 'center', 'color': 'gray'}}>
            {tipoUsuario === 'negocio'
              ? `$${props.data.precio}`
              : 'Confirmacion Pendiente'
            }
          </p>
          {tipoUsuario === 'negocio'
            ? <Button color="primary" size="large" onClick={() => setModalConfirmar(true)}>Confirmar</Button>
            : <Button color="primary" size="large" onClick={() => setModalCancelar(true)}>Cancelar Confirmacion</Button>
          }
        </CardBody>
        <div style={{'margin': '0 20px', 'display': 'flex', 'justify-content': 'space-between'}}>
          <CardFooter className={classes.textMuted}>
            Retirar antes del {props.data.validoHasta}
          </CardFooter>
        </div>
      </Card>

      <Dialog
        classes={{
          root: classes.center,
          paper: classes.modal
        }}
        open={modalConfirmar}
        TransitionComponent={Transition}
        keepMounted
        onClose={() => setModalConfirmar(false)}
        aria-labelledby="modal-slide-title"
        aria-describedby="modal-slide-description"
      >
        <DialogTitle
          id="classic-modal-slide-title"
          disableTypography
          className={classes.modalHeader}
        >
          <IconButton
            className={classes.modalCloseButton}
            key="close"
            aria-label="Close"
            color="inherit"
            onClick={() => setModalConfirmar(false)}
          >
            <Close className={classes.modalClose} />
          </IconButton>
          <h4 className={classes.modalTitle}>Confirmar canje de voucher</h4>
        </DialogTitle>
        <DialogContent
          id="modal-slide-description"
          className={classes.modalBody}
        >
          <h5>{`¿Desea confirmar el canje del voucher ${props.data.titulo}?`}</h5>
        </DialogContent>
        <DialogActions
          className={classes.modalFooter + ' ' + classes.modalFooterCenter}
        >
          <Button onClick={() => setModalConfirmar(false)}>Descartar</Button>
          <Button id="ConfirmarCanjeVoucher" onClick={confirmarCanjeDeVoucher} color="success">
            Confirmar
          </Button>
        </DialogActions>
      </Dialog>

      <Dialog
        classes={{
          root: classes.center,
          paper: classes.modal
        }}
        open={modalCancelar}
        TransitionComponent={Transition}
        keepMounted
        onClose={() => setModalCancelar(false)}
        aria-labelledby="modal-slide-title"
        aria-describedby="modal-slide-description"
      >
        <DialogTitle
          id="classic-modal-slide-title"
          disableTypography
          className={classes.modalHeader}
        >
          <IconButton
            className={classes.modalCloseButton}
            key="close"
            aria-label="Close"
            color="inherit"
            onClick={() => setModalCancelar(false)}
          >
            <Close className={classes.modalClose} />
          </IconButton>
          <h4 className={classes.modalTitle}>Cancelar solicitud de canje</h4>
        </DialogTitle>
        <DialogContent
          id="modal-slide-description"
          className={classes.modalBody}
        >
          <h5>¿Desea cancelar el canje del voucher {props.data.titulo}?</h5>
        </DialogContent>
        <DialogActions
          className={classes.modalFooter + ' ' + classes.modalFooterCenter}
        >
          <Button onClick={() => setModalCancelar(false)}>Descartar</Button>
          <Button onClick={cancelarSolicitudDeCanje} color="success">
            Confirmar
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
