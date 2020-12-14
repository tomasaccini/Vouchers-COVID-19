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
import voucherAPI from "../services/VoucherAPI";
import './styles.css';

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

function _item(item){
  return(
    <div className="itemList">
      <div className="itemInfo">
        <h1>{item.nombre}</h1>
        <h2>{item.descripcion}</h2>
      </div>
      <h1>{item.cantidad}</h1>
    </div>
  );
}

export default function VoucherSinDuenio(props) {
  const [modal, setModal] = React.useState(false);
  const [modalProducts, setModalProducts] = React.useState(false);
  const classes = useStyles();

  const onClickBuyButton = async () => {
    setModal(false)
    setModalProducts(false)
    // TODO pass actual parameters!!!!
    voucherAPI.comprarVoucher(1, 1)
  };

  return (
    <div>
      <Card className={classes.textCenter}>
        <CardHeader color={props.data.stock < 10 ? "danger" : "warning"}>QUEDAN {props.data.stock} VOUCHERS</CardHeader>
        <CardBody>
          <h2 className={classes.cardTitle}>{props.data.titulo}</h2>
          <p>
          {props.data.descripcion}
          </p>
          <Button color="primary" size="large" onClick={() => setModal(true)}>
            Comprar
          </Button>
          <Button color="primary" size="large" onClick={() => setModalProducts(true)}>
            Ver productos
          </Button>
        </CardBody>
        <CardFooter className={classes.textMuted}>
          Creado el {props.data.validoDesde} <br/>
          Finaliza el {props.data.validoHasta}
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
          <h3>${props.data.precio}</h3>
          <h5>¿Deseas confirmar la compra del voucher {props.data.titulo}?</h5>
        </DialogContent>
        <DialogActions
          className={classes.modalFooter + " " + classes.modalFooterCenter}
        >
          <Button onClick={() => setModal(false)}>Descartar</Button>
          <Button onClick={onClickBuyButton} color="success">
            Confirmar
          </Button>
        </DialogActions>
      </Dialog>

      <Dialog
        classes={{
          root: classes.center,
          paper: classes.modal
        }}
        className="productDialog"
        open={modalProducts}
        TransitionComponent={Transition}
        keepMounted
        onClose={() => setModalProducts(false)}
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
            onClick={() => setModalProducts(false)}
          >
            <Close className={classes.modalClose} />
          </IconButton>
          <h4 className={classes.modalTitle}>Productos</h4>
        </DialogTitle>
        <DialogContent
          id="modal-slide-description"
          className={classes.modalBody}
        >
          {props.data.items.map((item) =>
                    _item(item)
                )}
        </DialogContent>
        <DialogActions
          className={classes.modalFooter + " " + classes.modalFooterCenter}
        >
          <Button onClick={() => setModalProducts(false)}>Cerrar</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
