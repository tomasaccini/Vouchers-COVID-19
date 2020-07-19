import React, {Component} from 'react';

import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import VouchersList from "./VouchersList.js";
import UserNavbar from "./UserNavbar.js";
import {SERVER_URL} from 'config';
import voucherAPI from "../services/VoucherAPI";
import "./styles.css";

class UsersBuyVouchers extends Component {

    constructor() {
        super();
        this.state = { counterfoils: [] };
    }

    async componentDidMount() {
        const counterfoils = await this.getListOfVouchers();

        this.setState({ counterfoils })
    }

    async getListOfVouchers() {
        return await voucherAPI.getCounterfoils();

        const l = [
            {
                "Title": "2 Hamburguesas",
                "Description": "Vale por 2 hamburguesas con queso",
                "Price": 600,
                "CreationDate": "2020-04-01",
                "EndDate": "2020-06-01",
                "Stock": 4,
                "isOwned": false,
                "shopName": "Tio Felipe"
            },
            {
                "Title": "2 Cervezas",
                "Description": "Vale por 2 cervezas",
                "Price": 150,
                "CreationDate": "2020-04-10",
                "EndDate": "2020-12-31",
                "Stock": 100,
                "isOwned": false,
                "shopName": "Tio Felipe"
            },
            {
                "Title": "Sala de escape",
                "Description": "Vale por una visita a nuestras salas de escape",
                "Price": 300,
                "CreationDate": "2020-05-01",
                "EndDate": "2020-08-10",
                "Stock": 1,
                "isOwned": false,
                "shopName": "Tio Felipe"
            },
            {
                "Title": "10 paquetes de yerba",
                "Description": "Vale por 10 paquetes de yerba",
                "Price": 1000,
                "CreationDate": "2020-05-10",
                "EndDate": "2020-10-01",
                "Stock": 30,
                "isOwned": false,
                "shopName": "Tio Felipe"
            },{
                "Title": "2 Hamburguesas",
                "Description": "Vale por 2 hamburguesas con queso",
                "Price": 600,
                "CreationDate": "2020-04-01",
                "EndDate": "2020-06-01",
                "Stock": 4,
                "isOwned": false,
                "shopName": "Tio Felipe"
            },
            {
                "Title": "2 Cervezas",
                "Description": "Vale por 2 cervezas",
                "Price": 150,
                "CreationDate": "2020-04-10",
                "EndDate": "2020-12-31",
                "Stock": 100,
                "isOwned": false,
                "shopName": "Tio Felipe"
            },
            {
                "Title": "Sala de escape",
                "Description": "Vale por una visita a nuestras salas de escape",
                "Price": 300,
                "CreationDate": "2020-05-01",
                "EndDate": "2020-08-10",
                "Stock": 1,
                "isOwned": false,
                "shopName": "Tio Felipe"
            },
            {
                "Title": "10 paquetes de yerba",
                "Description": "Vale por 10 paquetes de yerba",
                "Price": 1000,
                "CreationDate": "2020-05-10",
                "EndDate": "2020-10-01",
                "Stock": 30,
                "isOwned": false,
                "shopName": "Tio Felipe"
            },{
                "Title": "2 Hamburguesas",
                "Description": "Vale por 2 hamburguesas con queso",
                "Price": 600,
                "CreationDate": "2020-04-01",
                "EndDate": "2020-06-01",
                "Stock": 4,
                "isOwned": false,
                "shopName": "Tio Felipe"
            },
            {
                "Title": "2 Cervezas",
                "Description": "Vale por 2 cervezas",
                "Price": 150,
                "CreationDate": "2020-04-10",
                "EndDate": "2020-12-31",
                "Stock": 100,
                "isOwned": false,
                "shopName": "Tio Felipe"
            },
            {
                "Title": "Sala de escape",
                "Description": "Vale por una visita a nuestras salas de escape",
                "Price": 300,
                "CreationDate": "2020-05-01",
                "EndDate": "2020-08-10",
                "Stock": 1,
                "isOwned": false,
                "shopName": "Tio Felipe"
            },
            {
                "Title": "10 paquetes de yerba",
                "Description": "Vale por 10 paquetes de yerba",
                "Price": 1000,
                "CreationDate": "2020-05-10",
                "EndDate": "2020-10-01",
                "Stock": 30,
                "isOwned": false,
                "shopName": "Tio Felipe"
            }
        ];

        return l;
    }


    render() {
        return (
            <div>
                <UserNavbar title="Comprar Vouchers" />
                <GridContainer className="vouchersGrid">
                    <GridItem>
                        <VouchersList vouchers={this.state.counterfoils}/>
                    </GridItem>
                </GridContainer>
            </div>
        );
    }
}

export default UsersBuyVouchers;
