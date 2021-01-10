import React, {Component} from 'react';
import covidIcon from '../images/covid.svg';
import '../css/home.css';

class Home extends Component {
    render(){
        return(
            <section className="home-page">
                <img className="rotate covid" src={covidIcon} alt="covid"/>
                <h1 className="home-title">Covid - Vouchers</h1>
                <p className="home-stitle">
                    Ayuda a tu local Amigo comprando vouchers de tus productos preferidos
                </p>
                <a href="/iniciarsesion">Ingresar</a>
            </section>
        )
    }
}

export default Home;