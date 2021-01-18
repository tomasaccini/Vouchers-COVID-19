import React from 'react';
import TarjetaTalonarioComprar from './TarjetaTalonarioComprar';
import TarjetaTalonarioNegocio from './TarjetaTalonarioNegocio';

export default function TarjetaTalonario(props) {

  if (localStorage.getItem('tipoUsuario') === 'negocio') {
    return <TarjetaTalonarioNegocio data={props.data} />;
  }

  return <TarjetaTalonarioComprar data={props.data} />;
}
