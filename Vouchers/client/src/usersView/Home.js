import React, {Component} from 'react';
import '../css/home.css';

class Home extends Component {
    render(){
        return(
            <section className="home-page">
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