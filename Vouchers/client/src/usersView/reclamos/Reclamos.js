import React, {Component} from 'react';

import UserNavbar from '../UserNavbar.js';
import MapSection from '../Map.js'
import GridContainer from 'components/Grid/GridContainer';
import VouchersList from '../VouchersList.js';
import BusinessInfo from '../BusinessInfo.js';
import GridItem from 'components/Grid/GridItem';
import '../styles.css';
import { Typography } from '@material-ui/core';
import businessAPI from '../../services/BusinessAPI.js';
import constantes from '../../utils/constantes';
import { ThemeProvider, ChatList, ChatListItem, AgentBar, Column, Avatar, TextComposer, Row, Fill, TextInput, Fit, SendButton, Subtitle, Title, MessageList, Message, MessageGroup, MessageTitle, MessageMedia, MessageText, MessageButton, MessageButtons } from '@livechat/ui-kit';
import ChatBox from './ChatBox'
import reclamoAPI from "../../services/ReclamoAPI";

const usuarioId = 1

// https://developers.livechat.com/docs/react-chat-ui-kit/
class Reclamos extends Component{

    constructor(props) {
        super(props);
        this.state = {

        }
    }

    async componentDidMount() {
        const reclamos = await this.getReclamos();

        this.setState({ reclamos: reclamos })
    }

    async getReclamos() {
        // !!!! change 1
        return reclamoAPI.getReclamos(usuarioId);
    }

    render() {
        /*
                const events = [];
        const parseUrl = (a) => "";
        const getAvatarForUser = (a, b) => ""
        const ownId = "id"
        const sendMessage = (a) => "";
        const users = [];
        const onMessageSend = (a) => "";
        const minimize = () => null;
        const currentAgent = {}
        const chatState = 'CHATTING';
        const rateGood = () => null;
        const rateBad = () => null;
        const rate = "";

         */
        const { reclamos } = this.state

        return (
          <div>
              <UserNavbar title={constantes.reclamosTitulo}/>
              <ThemeProvider>
                  <GridContainer className="vouchersGrid">
                      <GridItem xs={1}>
                      </GridItem>
                      <GridItem xs={4}>
                          <ChatList style={{maxWidth: 300}}>
                              { reclamos && reclamos.map(reclamo => {
                                  const email = usuarioId === reclamo.clienteId ? reclamo.negocioEmail : reclamo.clienteEmail

                                  return (
                                    <ChatListItem active>
                                        <Avatar letter={email[0]}/>
                                        <Column fill>
                                            <Row justify>
                                                <Title ellipsis>{email}</Title>
                                                <Subtitle nowrap>{reclamo.fechaUltimoMensaje.slice(11, 16)}</Subtitle>
                                            </Row>
                                            <Subtitle ellipsis>
                                                {reclamo.mensajes[reclamo.mensajes.length-1].texto}
                                            </Subtitle>
                                        </Column>
                                    </ChatListItem>
                                  )
                              })}
                          </ChatList>
                      </GridItem>
                      <GridItem xs={5}>
                          <ChatBox usuarioId={1}/>
                      </GridItem>
                      <GridItem xs={2}>
                      </GridItem>
                  </GridContainer>
              </ThemeProvider>
          </div>
        );
    }
}

export default Reclamos