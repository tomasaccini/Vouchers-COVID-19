import React, {Component} from 'react';

import NavbarUsuario from '../NavbarUsuario.js';
import GridContainer from 'components/Grid/GridContainer';
import GridItem from 'components/Grid/GridItem';
import '../styles.css';
import constantes from '../../utils/constantes';
import { ThemeProvider, ChatList, ChatListItem, Column, Avatar, Row, Subtitle, Title } from '@livechat/ui-kit';
import ReclamoChatBox from './ReclamoChatBox'
import reclamoAPI from '../../services/ReclamoAPI';
import fechasHelper from '../../utils/fechasHelper';
import Button from 'components/CustomButtons/Button.js';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import IconButton from '@material-ui/core/IconButton';
import Close from '@material-ui/icons/Close';
import DialogContent from '@material-ui/core/DialogContent';
import DialogActions from '@material-ui/core/DialogActions';
import modalStyle from 'assets/jss/material-kit-react/modalStyle.js';
import Slide from '@material-ui/core/Slide';
import { makeStyles } from '@material-ui/core/styles';
import {cardTitle} from "../../assets/jss/material-kit-react";
import ReclamoChatListItem from "./ReclamoChatListItem";

// https://developers.livechat.com/docs/react-chat-ui-kit/
class Reclamos extends Component{

    constructor(props) {
        super(props);
        this.state = {}
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
                      <GridItem xs={3}>
                      </GridItem>
                      <GridItem xs={3}>
                          <ChatList style={{maxWidth: 300}}>
                              { reclamos && reclamos.map((reclamo, index) => {
                                  const email = usuarioId === reclamo.clienteId ? reclamo.negocioEmail : reclamo.clienteEmail

                                  return <ReclamoChatListItem
                                    active={index === indexReclamoActivo}
                                    email={email}
                                    subtitulo={reclamo.mensajes[reclamo.mensajes.length-1].texto}
                                    fecha={fechasHelper.extraerHoraYMinutos(reclamo.fechaUltimoMensaje)}
                                    onclick={() => this.updateIndexReclamoActivo(index)}
                                  />
                              })}
                          </ChatList>
                      </GridItem>
                      <GridItem xs={3}>
                          <ReclamoChatBox
                            usuarioId={usuarioId}
                            mensajes={mensajes}
                            onSend={this.enviarMensajeBuilder()}
                          />
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
