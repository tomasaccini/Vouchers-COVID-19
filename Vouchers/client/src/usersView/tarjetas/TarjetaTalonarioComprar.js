import React from "react";
// material-ui components
import { makeStyles } from "@material-ui/core/styles";
import Slide from "@material-ui/core/Slide";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import IconButton from "@material-ui/core/IconButton";
import Close from "@material-ui/icons/Close";
// core components
import Card from "components/Card/Card.js";
import CardBody from "components/Card/CardBody.js";
import CardHeader from "components/Card/CardHeader.js";
import CardFooter from "components/Card/CardFooter.js";
import Button from "components/CustomButtons/Button.js";
import modalStyle from "assets/jss/material-kit-react/modalStyle.js";

import { cardTitle } from "assets/jss/material-kit-react.js";
import voucherAPI from "../../services/VoucherAPI";
import '../styles.css';
import ProductListDialog from '../../dialogs/ProductListDialog';
import { Redirect } from "react-router-dom";
import Rating from '@material-ui/lab/Rating';
import navegacion from 'utils/navegacion';

const styles = {
  cardTitle,
  textCenter: {
    textAlign: "center"
  },
  textMuted: {
    color: "#6c757d"
  },
  ...modalStyle
};

const useStyles = makeStyles(styles);

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="down" ref={ref} {...props} />;
});

export default function TarjetaTalonarioComprar(props) {
  const [modal, setModal] = React.useState(false);
  const [modalProducts, setModalProducts] = React.useState(false);
  const [comprado, setComprado] = React.useState(false);
  const classes = useStyles();

  const usuarioId = localStorage.getItem('userId');
  const tipoUsuario = localStorage.getItem('tipoUsuario');
  const habilitarCompra = tipoUsuario === 'cliente';
  const deshabilitarBotonComprar = !habilitarCompra || props.data.stock <= 0;

  const onClickBuyButton = async () => {
    setModal(false);
    setModalProducts(false);
    const voucherCreado = await voucherAPI.comprarVoucher(usuarioId, props.data.id);

    if (voucherCreado !== null) {
      setComprado(true);
    }
  };

  if (comprado) {
    return <Redirect to={navegacion.getClienteCanjearVoucherUrl()} />
  }

  return (
    <div>
      <Card className={classes.textCenter}>
        <CardBody>
          <CardHeader color={props.data.stock < 10 ? "danger" : "warning"}><a style={{'text-decoration': 'none', 'color': 'white',}} href={navegacion.getNegocioPerfilUrl(props.data.negocioId)} target="_blank" rel="noopener noreferrer"><b>{props.data.negocioNombre}</b></a></CardHeader>
          <h2 className={classes.cardTitle}>{props.data.titulo}</h2>
          <p style={{'font-size': '20px', 'font-weight': 'bold'}}>
          ${props.data.precio}
          </p>
          <Button color="primary" size="large" disabled={deshabilitarBotonComprar} onClick={() => setModal(true)}>
            Comprar
          </Button>
          <Button color="primary" size="large" onClick={() => setModalProducts(true)}>
            Ver productos
          </Button>
          <br></br>
          <Rating
            name="simple-controlled"
            value={props.data.rating}
            readOnly={true} 
          />
        </CardBody>
        <CardFooter className={classes.textMuted}>
          <div style={{'width': '100%', 'display': 'flex', 'justify-content': 'space-between'}}>
            <div>
              Creado el {props.data.validoDesde} <br/>
              Finaliza el {props.data.validoHasta}
            </div>
            <div>
              QUEDAN {props.data.stock} VOUCHERS
            </div>
          </div>
        </CardFooter>
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
          <h4 className={classes.modalTitle}>Confirmar compra</h4>
        </DialogTitle>
        <DialogContent
          id="modal-slide-description"
          className={classes.modalBody}
        >
          <div style={{'display': 'flex', 'flex-direction': 'column',}}>
            <h5>{`¿Desea confirmar la compra del voucher ${props.data.titulo}?`}</h5>
            <p style={{'font-size': '20px', 'font-weight': 'bold', 'align-self': 'center'}}>${props.data.precio}</p>
          </div>
        </DialogContent>
        <DialogActions
          className={classes.modalFooter + " " + classes.modalFooterCenter}
        >
          <Button onClick={() => setModal(false)}>Descartar</Button>
          <Button id="ConfirmarCompraVoucher" onClick={onClickBuyButton} color="success">
            Confirmar
          </Button>
        </DialogActions>
      </Dialog>

      <ProductListDialog 
        classes={classes} 
        setModalProducts={setModalProducts} 
        modalProducts={modalProducts}
        items={props.data.items}
        transition={Transition}
      />
    </div>
  );
}
