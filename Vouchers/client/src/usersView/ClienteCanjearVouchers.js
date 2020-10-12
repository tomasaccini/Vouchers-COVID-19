import React, {Component} from 'react';
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import VouchersList from "./VouchersList.js";
import UserNavbar from "./UserNavbar.js";
import "./styles.css";
import constantes from '../utils/constantes'
import voucherAPI from "../services/VoucherAPI";

class ClienteCanjearVouchers extends Component {

    constructor() {
        super();
        this.state = { vouchers: [] };
    }

    async componentDidMount() {
        const vouchers = await this.getListOfVouchers();
        console.log('!!!!', vouchers)

        this.setState({ vouchers })
    }

    async getListOfVouchers() {
        return await voucherAPI.getVouchers(null);

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
                <UserNavbar title={constantes.canjearVouchersTitulo} />
                <GridContainer className="vouchersGrid">
                    <GridItem>
                        <VouchersList vouchers={this.state.vouchers}/>
                    </GridItem>
                </GridContainer>
            </div>
        );
    }
}

export default ClienteCanjearVouchers;
