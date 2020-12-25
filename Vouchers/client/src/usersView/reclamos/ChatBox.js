import React, {Component} from 'react';
import {
  Fill, Fit,
  Message,
  MessageGroup,
  MessageList,
  MessageText,
  Row, SendButton, TextComposer, TextInput
} from '@livechat/ui-kit';
import fechasHelper from '../../utils/fechasHelper';

class ChatBox extends Component {

  constructor(props) {
    super(props);

    this.state = {
      mensajes: [
        {
          duenioEmail: "Asdsaldas",
          texto: "Este es el mensaje",
          duenioId: 3,
        },
        {
          duenioEmail: "Asdsaldas",
          texto: "Este es el mensaje",
          duenioId: 1,
        },
      ],
    };

    // TODO hacerlo bien
    this.estilos = {
      false: { background: 'lightgrey', borderRadius: '6px' },
      true: { background: 'lightblue', color: 'white', borderRadius: '6px' },
    }
  }

  renderMensajeAjeno(mensaje) {
    return (
      <MessageGroup
        avatarLetter={mensaje.duenioEmail[0]}
        onlyFirstWithMeta
      >
        <Message authorName={fechasHelper.extraerAnioMesDia(mensaje.fecha)} date={fechasHelper.extraerHoraYMinutos(mensaje.fecha)}>
          <MessageText style={this.estilos[false]}>
            {mensaje.texto}
          </MessageText>
        </Message>
      </MessageGroup>
    )
  }

  renderMensajePropio(mensaje) {
    return (
      <MessageGroup isOwn={true}
                    onlyFirstWithMeta
      >
        <Message isOwn={true} authorName={`Enviado ${fechasHelper.extraerAnioMesDia(mensaje.fecha)}`} date={fechasHelper.extraerHoraYMinutos(mensaje.fecha)}>
          <MessageText style={this.estilos[true]}>
            {mensaje.texto}
          </MessageText>
        </Message>
      </MessageGroup>
    )
  }

  render() {
    const { usuarioId, mensajes, onSend } = this.props;

    return (
      <div style={{ maxWidth: '100%', height: '400px' }}>
        <MessageList active>
          { mensajes.map(mensaje => {
            const esDuenio = usuarioId === mensaje.duenioId
            if (esDuenio) {
              return this.renderMensajePropio(mensaje)
            }

            return this.renderMensajeAjeno(mensaje)
          })}
        </MessageList>
        <TextComposer onSend={onSend}>
          <Row align="center">
            <Fill>
              <TextInput />
            </Fill>
            <Fit>
              <SendButton />
            </Fit>
          </Row>
        </TextComposer>
      </div>
    )
  }
}

export default ChatBox
