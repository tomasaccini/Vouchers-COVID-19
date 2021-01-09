import React from 'react';
import TarjetaVoucherCanjeable from "./TarjetaVoucherCanjeable";
import TarjetaVoucherConfirmable from "./TarjetaVoucherConfirmable";
import TarjetaVoucherCanjeada from "./TarjetaVoucherCanjeada";
import TarjetaVoucherReclamoAbierto from "./TarjetaVoucherReclamoAbierto";

export default function TarjetaVoucher(props) {
  if (props.data.reclamoAbierto) {
    return <TarjetaVoucherReclamoAbierto data={props.data} />;
  }

  if (props.data.state === 'Comprado') {
    return <TarjetaVoucherCanjeable data={props.data} />;
  }

  if (props.data.state === 'ConfirmacionPendiente') {
    return <TarjetaVoucherConfirmable data={props.data} />;
  }

  if (props.data.state === 'Canjeado') {
    return <TarjetaVoucherCanjeada data={props.data} />;
  }

  return null;
}
