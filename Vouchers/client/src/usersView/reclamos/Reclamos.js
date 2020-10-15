import React, {Component} from 'react';

import UserNavbar from '../UserNavbar.js';
import MapSection from '../Map.js'
import GridContainer from "components/Grid/GridContainer";
import VouchersList from '../VouchersList.js';
import BusinessInfo from '../BusinessInfo.js';
import GridItem from "components/Grid/GridItem";
import "../styles.css";
import { Typography } from '@material-ui/core';
import businessAPI from '../../services/BusinessAPI.js';
import constantes from "../../utils/constantes";
import { ThemeProvider, AgentBar, Column, Avatar, Subtitle, Title, MessageList, Message, MessageGroup, MessageTitle, MessageMedia, MessageText, MessageButton, MessageButtons } from "@livechat/ui-kit";


class Reclamos extends Component{

    constructor() {
        super();
        this.location = {
            "address": 'Costumbres Argentinas',
            "lat": -34.599373,
            "lng": -58.438230,
          } // here until i learn react properly
        this.state = { profile: {}};
    }

    async componentDidMount() {
        this.setState({ profile: {} })
    }

    render() {
        return (
            <div>
                <UserNavbar title={constantes.reclamosTitulo} />
                <GridContainer className="vouchersGrid">
                    <GridItem>
                        <ThemeProvider>
                            <AgentBar>
                                <Avatar imgUrl="https://livechat.s3.amazonaws.com/default/avatars/male_8.jpg" />
                                <Column>
                                    <Title>{'Jon Snow'}</Title>
                                    <Subtitle>{'Support hero'}</Subtitle>
                                </Column>
                            </AgentBar>
                        </ThemeProvider>
                    </GridItem>
                </GridContainer>
            </div>
        );
    }

}

export default Reclamos