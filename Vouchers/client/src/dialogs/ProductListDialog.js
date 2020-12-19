import React from "react";

import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import IconButton from "@material-ui/core/IconButton";
import Button from "components/CustomButtons/Button.js";
import Close from "@material-ui/icons/Close";

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

function ProductListDialog(props){
    return(
        <Dialog
            classes={{
            root: props.classes.center,
            paper: props.classes.modal
            }}
            className="productDialog"
            open={props.modalProducts}
            TransitionComponent={props.transition}
            keepMounted
            onClose={() => props.setModalProducts(false)}
            aria-labelledby="modal-slide-title"
            aria-describedby="modal-slide-description"
        >
        <DialogTitle
          id="classic-modal-slide-title"
          disableTypography
          className={props.classes.modalHeader}
        >
          <IconButton
            className={props.classes.modalCloseButton}
            key="close"
            aria-label="Close"
            color="inherit"
            onClick={() => props.setModalProducts(false)}
          >
            <Close className={props.classes.modalClose} />
          </IconButton>
          <h4 className={props.classes.modalTitle}>Productos</h4>
        </DialogTitle>
        <DialogContent
          id="modal-slide-description"
          className={props.classes.modalBody}
        >
          {props.items ? props.items.map((item) =>
                    _item(item)) :
                    <h3> No tiene productos </h3>}
        </DialogContent>
        <DialogActions
          className={props.classes.modalFooter + " " + props.classes.modalFooterCenter}
        >
          <Button onClick={() => props.setModalProducts(false)}>Cerrar</Button>
        </DialogActions>
      </Dialog>
    );
}

export default ProductListDialog;