import React, {Component} from 'react';

import NavbarUsuario from '../NavbarUsuario.js';
import GridContainer from 'components/Grid/GridContainer';
import GridItem from 'components/Grid/GridItem';
import '../styles.css';
import constantes from '../../utils/constantes';
import { ThemeProvider, ChatList, ChatListItem, Column, Avatar, Row, Subtitle, Title } from '@livechat/ui-kit';
import ChatBox from './ChatBox'
import reclamoAPI from '../../services/ReclamoAPI';
import fechasHelper from '../../utils/fechasHelper';
import Button from 'components/CustomButtons/Button.js';

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
        const { usuarioId } = this.props;
        const { reclamos, indexReclamoActivo } = this.state;
        const mensajes = reclamos === undefined || reclamos.length === 0 ? [] : reclamos[indexReclamoActivo].mensajes;

        return (
          <div className="reclamos">
              <NavbarUsuario title={constantes.reclamosTitulo}/>
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
                                        <Column fill style={{'width': '100%'}}>
                                            <Row justify>
                                                <Title ellipsis style={{'dummy': 'Truncate size of Title to up to n characters !!!!'}}>{email}</Title>
                                                <Subtitle nowrap>{fechasHelper.extraerHoraYMinutos(reclamo.fechaUltimoMensaje)}</Subtitle>
                                                <Button color="rose" size="sm">X</Button>
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
