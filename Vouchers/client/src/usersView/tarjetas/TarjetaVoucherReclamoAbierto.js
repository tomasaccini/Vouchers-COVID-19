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
import CerrarReclamoButton from "../CerrarReclamoButton";

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

export default function TarjetaVoucherReclamoAbierto(props) {
  const [modal, setModal] = React.useState(false);
  const [actualizar, setActualizar] = React.useState(false);
  const classes = useStyles();

  // TODO: definir si lo hacemos por aca tambien o no.
  const cerrarReclamo = async () => {
  }

  if (actualizar) {
    window.location.replace(navegacion.getVouchersConfirmablesUrl());
  }

  return (
    <div>
      <Card className={classes.textCenter} style={{'min-height': '245px'}}>
        <CardHeader color="danger"><a style={{'text-decoration': 'none', 'color': 'white',}} href={navegacion.getNegocioPerfilUrl(props.data.negocioId)} target="_blank" rel="noopener noreferrer"><b>{props.data.negocioNombre}</b></a></CardHeader>
        <CardBody>
          <h2 className={classes.cardTitle}>{props.data.titulo}</h2>
          <p style={{'font-size': '20px', 'font-weight': 'bold', 'align-self': 'center', 'color': 'gray'}}>
            Reclamo Abierto
          </p>
          <Button style={{'visibility': 'hidden'}} color="primary" size="lg" onClick={() => setModal(true)}>
            Cerrar Reclamo
          </Button>
        </CardBody>
        <div style={{'margin': '0 20px', 'display': 'flex', 'justify-content': 'space-between'}}>
          <CardFooter className={classes.textMuted}>
            Retirar antes del {props.data.validoHasta}
          </CardFooter>
          <div style={{'margin-right': '15px'}}>
            <CerrarReclamoButton tituloVoucher={props.data.titulo} reclamoId={props.data.reclamo.id} />
          </div>
        </div>
      </Card>

      <Dialog
        classes={{
          root: classes.center,
          paper: classes.modal
        }}
        open={modal}
        TransitionComponent={Transition}
        keepMounted
        onClose={() => setModal(false)}
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
            onClick={() => setModal(false)}
          >
            <Close className={classes.modalClose} />
          </IconButton>
          <h4 className={classes.modalTitle}>Confirmar canje de voucher</h4>
        </DialogTitle>
        <DialogContent
          id="modal-slide-description"
          className={classes.modalBody}
        >
          <h5>¿Desea confirmar el canje del voucher {props.data.titulo}?</h5>
        </DialogContent>
        <DialogActions
          className={classes.modalFooter + ' ' + classes.modalFooterCenter}
        >
          <Button onClick={() => setModal(false)}>Descartar</Button>
          <Button onClick={cerrarReclamo} color="success">
            Confirmar
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
