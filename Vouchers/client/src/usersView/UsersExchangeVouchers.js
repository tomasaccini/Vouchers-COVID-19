import React, {Component} from 'react';
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import VouchersList from "./VouchersList.js";
import UserNavbar from "./UserNavbar.js";
import "./styles.css";

class UsersExchangeVouchers extends Component {
    getListOfVouchers() {
        const l = [
            {
                "Title": "2 Hamburguesas",
                "Description": "Vale por 2 hamburguesas con queso",
                "Price": 600,
                "CreationDate": "2020-04-01",
                "EndDate": "2020-06-01",
                "Stock": 4,
                "isOwned": true,
                "shopName": "Wunderbar"
            },
            {
                "Title": "2 Cervezas",
                "Description": "Vale por 2 cervezas",
                "Price": 150,
                "CreationDate": "2020-04-10",
                "EndDate": "2020-12-31",
                "Stock": 100,
                "isOwned": true,
                "shopName": "Tio Felipe"
            }
        ]
        return l;
    }


    render() {
        return (
            <div>
                <UserNavbar title="Canjear Vouchers" />
                <GridContainer className="vouchersGrid">
                    <GridItem>
                        <VouchersList vouchers={this.getListOfVouchers()}/>
                    </GridItem>
                </GridContainer>
            </div>
        );
    }
}

export default UsersExchangeVouchers;
