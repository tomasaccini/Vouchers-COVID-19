import React from 'react';
// material-ui components
import { makeStyles } from '@material-ui/core/styles';
// core components
import Card from 'components/Card/Card.js';
import CardBody from 'components/Card/CardBody.js';
import CardHeader from 'components/Card/CardHeader.js';
import CardFooter from 'components/Card/CardFooter.js';
import Rating from '@material-ui/lab/Rating';
import '../styles.css';

import { cardTitle } from 'assets/jss/material-kit-react.js';
import navegacion from "../../utils/navegacion";
import Button from "../../components/CustomButtons/Button";
import modalStyle from "../../assets/jss/material-kit-react/modalStyle";
import Slide from "@material-ui/core/Slide";
import ProductListDialog from '../../dialogs/ProductListDialog';
import voucherAPI from "../../services/VoucherAPI";


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


export default function TarjetaVoucherCanjeado(props) {
  const [modalProducts, setModalProducts] = React.useState(false);
  const classes = useStyles();

  const cambiarRating = async (rating) => {
    console.log(props.data.id);
    await voucherAPI.puntuarVoucher(props.data.id, rating);
    window.location.reload(false);
  }

  return (
    <div>
      <Card className={classes.textCenter} style={{'min-height': '220px'}}>
        <CardHeader color="info"><a style={{'text-decoration': 'none', 'color': 'white',}} href={navegacion.getNegocioPerfilUrl(props.data.negocioId)} target="_blank" rel="noopener noreferrer"><b>{props.data.negocioNombre}</b></a></CardHeader>
        <CardBody>
          <h2 className={classes.cardTitle}>{props.data.titulo}</h2>
          <p style={{'font-size': '20px', 'font-weight': 'bold', 'align-self': 'center'}}>${props.data.precio}</p>
          <Button color="primary" size="large" onClick={() => setModalProducts(true)}>
            Ver productos
          </Button>
          <br></br>
          <Rating
            name="simple-controlled"
            value={props.data.rating}
            onChange={(event, nuevoValor) => cambiarRating(nuevoValor)}
            readOnly={props.data.rating !== 0} 
          />
        </CardBody>
        <div style={{'margin': '0 20px', 'display': 'flex', 'justify-content': 'space-between'}}>
          <CardFooter className={classes.textMuted}>
            Retirado antes del {props.data.validoHasta}
          </CardFooter>
        </div>
      </Card>
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
