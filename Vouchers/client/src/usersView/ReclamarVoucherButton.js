import React from "react";
import IconButton from '@material-ui/core/IconButton';
import FeedbackIcon from '@material-ui/icons/Feedback';
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import Close from "@material-ui/icons/Close";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "../components/CustomButtons/Button";
import {cardTitle} from "../assets/jss/material-kit-react";
import modalStyle from "../assets/jss/material-kit-react/modalStyle";
import {makeStyles} from "@material-ui/core/styles";
import Slide from "@material-ui/core/Slide";
import voucherAPI from "../services/VoucherAPI";
import reclamoAPI from "../services/ReclamoAPI";

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

export default function ReclamarVoucherButton(props) {
  const [modal, setModal] = React.useState(false);
  const classes = useStyles();
  const { voucherId } = props;

  const usuarioId = localStorage.getItem('userId');

  const iniciarReclamo = async () =>{
    setModal(false);
    reclamoAPI.iniciarReclamo(voucherId, usuarioId);
  }

  return (
    <div>
      <IconButton color="action" component="span" onClick={() => setModal(true)}>
        <FeedbackIcon />
      </IconButton>

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
          <h4 className={classes.modalTitle}>Iniciar reclamo</h4>
        </DialogTitle>
        <DialogContent
          id="modal-slide-description"
          className={classes.modalBody}
        >
          <h5>¿Desea iniciar un reclamo para este voucher?</h5>
        </DialogContent>
        <DialogActions
          className={classes.modalFooter + " " + classes.modalFooterCenter}
        >
          <Button onClick={() => setModal(false)}>Cancelar</Button>
          <Button onClick={() => iniciarReclamo(voucherId)} color="success">
            Confirmar
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  )
}
