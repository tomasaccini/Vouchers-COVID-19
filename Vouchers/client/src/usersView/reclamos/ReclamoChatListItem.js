import React from "react";
import {Avatar, ChatListItem, Column, Row, Subtitle, Title} from "@livechat/ui-kit";
import fechasHelper from "../../utils/fechasHelper";
import Button from "../../components/CustomButtons/Button";
import {cardTitle} from "../../assets/jss/material-kit-react";
import modalStyle from "../../assets/jss/material-kit-react/modalStyle";
import reclamoAPI from '../../services/ReclamoAPI';
import {makeStyles} from "@material-ui/core/styles";
import Slide from "@material-ui/core/Slide";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import IconButton from "@material-ui/core/IconButton";
import Close from "@material-ui/icons/Close";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import {Redirect} from "react-router-dom";
import navegacion from "../../utils/navegacion";
import CerrarReclamoButton from "../CerrarReclamoButton";


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

export default function ReclamoChatListItem(props) {
  const [modal, setModal] = React.useState(false);
  const [actualizar, setActualizar] = React.useState(false);
  const classes = useStyles();
  const { email, onclick, active, subtitulo, fecha, reclamoId } = props;

  const cerrarReclamo = async (reclamoId) => {
    setModal(false);
    const usuarioId = localStorage.getItem('userId');
    await reclamoAPI.cerrarReclamo(reclamoId, usuarioId);
    setActualizar(true);
  };

  const esCliente = localStorage.getItem('tipoUsuario') === 'cliente';

  if (actualizar) {
    return <Redirect to={navegacion.getClienteCanjearVoucherUrl()} />
  }

  return (
    <ChatListItem active={active} onClick={onclick}>
      <Avatar letter={email[0]}/>
      <Column fill style={{'width': '100%'}}>
        <Row justify>
          <Title ellipsis>{email}</Title>
          <div style={{'display': 'flex'}}>
            <Subtitle nowrap>{fecha}</Subtitle>
            { !esCliente ? null : <CerrarReclamoButton tituloVoucher={subtitulo} reclamoId={reclamoId} /> }
          </div>
        </Row>
        <Subtitle ellipsis>
          {subtitulo}
        </Subtitle>
      </Column>

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
          <h4 className={classes.modalTitle}>Cerrar reclamo</h4>
        </DialogTitle>
        <DialogContent
          id="modal-slide-description"
          className={classes.modalBody}
        >
          <h5>¿Desea confirmar que su reclamo ha sido resuelto?</h5>
        </DialogContent>
        <DialogActions
          className={classes.modalFooter + " " + classes.modalFooterCenter}
        >
          <Button onClick={() => setModal(false)}>Cancelar</Button>
          <Button onClick={() => cerrarReclamo(reclamoId)} color="success">
            Confirmar
          </Button>
        </DialogActions>
      </Dialog>
    </ChatListItem>
  );
}
