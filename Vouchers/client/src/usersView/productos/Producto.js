import React from 'react';
import '../styles.css';

function Producto(props){
    const producto = props.data;
    return(
        <div className="productoInd">
            <h2>{producto.titulo}</h2>
            <p>{producto.descripcion}</p>
        </div>
    )
}

export default Producto;