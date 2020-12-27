import React from 'react';
import TarjetaVoucherCanjeable from "./TarjetaVoucherCanjeable";
import TarjetaVoucherConfirmable from "./TarjetaVoucherConfirmable";

export default function TarjetaVoucher(props) {
  console.log(props.data.state);
  if (props.data.state === 'Comprado') {
    return <TarjetaVoucherCanjeable data={props.data} />;
  }

  if (props.data.state === 'ConfirmacionPendiente') {
    return <TarjetaVoucherConfirmable data={props.data} />;
  }

  return null;
}
