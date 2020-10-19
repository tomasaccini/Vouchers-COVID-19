import React, {Component} from 'react';
import {
  Fill, Fit,
  Message, MessageButton,
  MessageButtons,
  MessageGroup,
  MessageList,
  MessageMedia,
  MessageText,
  MessageTitle, Row, SendButton, TextComposer, TextInput
} from '@livechat/ui-kit';
import GridItem from '../../components/Grid/GridItem';


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
        <Message authorName={mensaje.duenioEmail} date="21:37">
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
        <Message isOwn={true} authorName={"Enviado"} date="21:37">
          <MessageText style={this.estilos[true]}>
            {mensaje.texto}
          </MessageText>
        </Message>
      </MessageGroup>
    )
  }

  render() {
    const { usuarioId } = this.props
    const { mensajes } = this.state

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
        <TextComposer onSend={() => "!!!!"}>
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
