import React from "react";
// material-ui components
import { makeStyles } from "@material-ui/core/styles";
// core components
import Card from "components/Card/Card.js";
import CardBody from "components/Card/CardBody.js";
import CardHeader from "components/Card/CardHeader.js";
import CardFooter from "components/Card/CardFooter.js";
import Button from "components/CustomButtons/Button.js";

import { cardTitle } from "assets/jss/material-kit-react.js";

const styles = {
  cardTitle,
  textCenter: {
    textAlign: "center"
  },
  textMuted: {
    color: "#6c757d"
  },
};

const useStyles = makeStyles(styles);

export default function VoucherCard(props) {
  const classes = useStyles();
  return (
    <Card className={classes.textCenter}>
      <CardHeader color={props.data.Stock < 10 ? "danger" : "warning"}>QUEDAN {props.data.Stock} VOUCHERS</CardHeader>
      <CardBody>
        <h2 className={classes.cardTitle}>{props.data.Title}</h2>
        <p>
        {props.data.Description}
        </p>
        <Button color="primary" size="large">Comprar</Button>
      </CardBody>
      <CardFooter className={classes.textMuted}>
        Creado el {props.data.CreationDate} <br/>
        Finaliza el {props.data.EndDate}
      </CardFooter>
    </Card>
  );
}
