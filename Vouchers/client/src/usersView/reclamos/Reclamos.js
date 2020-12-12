import React, {Component} from 'react';

import UserNavbar from '../UserNavbar.js';
import MapSection from '../Map.js'
import GridContainer from 'components/Grid/GridContainer';
import ListaVouchers from '../ListaVouchers.js';
import InformacionNegocio from '../InformacionNegocio.js';
import GridItem from 'components/Grid/GridItem';
import '../styles.css';
import { Typography } from '@material-ui/core';
import negocioAPI from '../../services/NegocioAPI.js';
import constantes from '../../utils/constantes';
import { ThemeProvider, ChatList, ChatListItem, AgentBar, Column, Avatar, TextComposer, Row, Fill, TextInput, Fit, SendButton, Subtitle, Title, MessageList, Message, MessageGroup, MessageTitle, MessageMedia, MessageText, MessageButton, MessageButtons } from '@livechat/ui-kit';
import ChatBox from './ChatBox'
import reclamoAPI from '../../services/ReclamoAPI';
import fechasHelper from '../../utils/fechasHelper';

// https://developers.livechat.com/docs/react-chat-ui-kit/
class Reclamos extends Component{

    constructor(props) {
        super(props);
        this.state = {
        }
    }

    async componentDidMount() {
        await this.updateReclamos(this.props.usuarioId, this.setState.bind(this));
    }

    async updateReclamos(usuarioId, setState) {
        const reclamos = (await reclamoAPI.getReclamos(usuarioId)).sort((r1, r2) => -r1.fechaUltimoMensaje.localeCompare(r2.fechaUltimoMensaje));
        const indexReclamoActivo = reclamos.length === 0 ? -1 : 0;

        setState({ reclamos, indexReclamoActivo });
    }

    enviarMensajeBuilder() {
        const { usuarioId } = this.props;
        const { reclamos, indexReclamoActivo } = this.state;
        if (reclamos === undefined || reclamos.length === 0) return;

        const reclamoActivo = reclamos[indexReclamoActivo];
        const setState = this.setState.bind(this);
        const updateReclamos = this.updateReclamos;

        async function innerEnviarMensaje(mensaje) {
            await reclamoAPI.enviarMensaje(reclamoActivo.id, usuarioId, mensaje);
            updateReclamos(usuarioId, setState);
        }

        return innerEnviarMensaje;
    }

    updateIndexReclamoActivo(nuevoIndex) {
        this.setState({ indexReclamoActivo: nuevoIndex })
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
        const { usuarioId } = this.props;
        const { reclamos, indexReclamoActivo } = this.state;
        const mensajes = reclamos === undefined || reclamos.length === 0 ? [] : reclamos[indexReclamoActivo].mensajes;

        return (
          <div>
              <UserNavbar title={constantes.reclamosTitulo}/>
              <ThemeProvider>
                  <GridContainer className="vouchersGrid">
                      <GridItem xs={1}>
                      </GridItem>
                      <GridItem xs={4}>
                          <ChatList style={{maxWidth: 300}}>
                              { reclamos && reclamos.map((reclamo, index) => {
                                  const email = usuarioId === reclamo.clienteId ? reclamo.negocioEmail : reclamo.clienteEmail

                                  return (
                                    <ChatListItem active={index === indexReclamoActivo} onClick={() => this.updateIndexReclamoActivo(index)}>
                                        <Avatar letter={email[0]}/>
                                        <Column fill>
                                            <Row justify>
                                                <Title ellipsis>{email}</Title>
                                                <Subtitle nowrap>{fechasHelper.extraerHoraYMinutos(reclamo.fechaUltimoMensaje)}</Subtitle>
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
                          <ChatBox usuarioId={usuarioId} mensajes={mensajes} onSend={this.enviarMensajeBuilder()} />
                      </GridItem>
                      <GridItem xs={2}>
                      </GridItem>
                  </GridContainer>
              </ThemeProvider>
          </div>
        );
    }
}

export default Reclamos;
