import React from 'react';
import '../usersView/styles.css';

const SearchBar = ({nombre, input, onChange}) => {
  return (
    <input 
     className="searchBar"
     key="random1"
     value={input}
     placeholder={"Buscar por " + nombre}
     onChange={(e) => onChange(e.target.value)}
    />
  );
}

export default SearchBar