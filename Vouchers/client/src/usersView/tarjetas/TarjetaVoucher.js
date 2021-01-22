import React from 'react';
import TarjetaVoucherCanjeable from "./TarjetaVoucherCanjeable";
import TarjetaVoucherConfirmable from "./TarjetaVoucherConfirmable";
import TarjetaVoucherCanjeado from "./TarjetaVoucherCanjeado";
import TarjetaVoucherReclamoAbierto from "./TarjetaVoucherReclamoAbierto";

export default function TarjetaVoucher(props) {
  if (props.data.reclamoAbierto) {
    return <TarjetaVoucherReclamoAbierto data={props.data} />;
  }

  if (props.data.estado === 'Comprado') {
    return <TarjetaVoucherCanjeable data={props.data} />;
  }

  if (props.data.estado === 'ConfirmacionPendiente') {
    return <TarjetaVoucherConfirmable data={props.data} />;
  }

  if (props.data.estado === 'Canjeado') {
    return <TarjetaVoucherCanjeado data={props.data} />;
  }

  return null;
}
