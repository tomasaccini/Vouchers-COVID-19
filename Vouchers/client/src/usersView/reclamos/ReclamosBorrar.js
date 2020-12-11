import React, {Component} from 'react';

import UserNavbar from '../UserNavbar.js';
import MapSection from '../Map.js'
import GridContainer from 'components/Grid/GridContainer';
import VouchersList from '../VouchersList.js';
import BusinessInfo from '../BusinessInfo.js';
import GridItem from 'components/Grid/GridItem';
import '../styles.css';
import { Typography } from '@material-ui/core';
import negocioAPI from '../../services/NegocioAPI.js';
import constantes from '../../utils/constantes';
import { ThemeProvider, ChatList, ChatListItem, AgentBar, Column, Avatar, TextComposer, Row, Fill, TextInput, Fit, SendButton, Subtitle, Title, MessageList, Message, MessageGroup, MessageTitle, MessageMedia, MessageText, MessageButton, MessageButtons } from '@livechat/ui-kit';
import ChatBox from './ChatBox'


// https://developers.livechat.com/docs/react-chat-ui-kit/
class Reclamos extends Component{

    constructor(props) {
        super();
        this.state = { profile: {}};
    }

    async componentDidMount() {
        this.setState({ profile: {} })
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
        return (
          <div>
              <UserNavbar title={constantes.reclamosTitulo}/>
              <ThemeProvider>
                  <GridContainer className="vouchersGrid">
                      <GridItem xs={1}>
                      </GridItem>
                      <GridItem xs={4}>
                          <ChatList style={{maxWidth: 300}}>
                              <ChatListItem>
                                  <Avatar letter="K"/>
                                  <Column fill>
                                      <Row justify>
                                          <Title ellipsis>{'Konrad'}</Title>
                                          <Subtitle nowrap>{'14:31 PM'}</Subtitle>
                                      </Row>
                                      <Subtitle ellipsis>
                                          {'Hello, how can I help you? We have a lot to talk about'}
                                      </Subtitle>
                                  </Column>
                              </ChatListItem>
                              <ChatListItem active>
                                  <Avatar letter="J"/>
                                  <Column fill>
                                      <Row justify>
                                          <Title ellipsis>{'Andrew'}</Title>
                                          <Subtitle nowrap>{'14:31 PM'}</Subtitle>
                                      </Row>
                                      <Subtitle ellipsis>{'actually I just emailed you back'}</Subtitle>
                                  </Column>
                              </ChatListItem>
                              <ChatListItem>
                                  <Avatar imgUrl="https://livechat.s3.amazonaws.com/default/avatars/male_8.jpg"/>
                                  <Column fill>
                                      <Row justify>
                                          <Title ellipsis>{'Michael'}</Title>
                                          <Subtitle nowrap>{'14:31 PM'}</Subtitle>
                                      </Row>
                                      <Subtitle ellipsis>
                                          {"Ok, thanks for the details, I'll get back to you tomorrow."}
                                      </Subtitle>
                                  </Column>
                              </ChatListItem>
                          </ChatList>
                      </GridItem>
                      <GridItem xs={5}>
                          <div style={{maxWidth: '100%', height: '400px'}}>
                              <MessageList active>
                                  <MessageGroup
                                    avatar="https://livechat.s3.amazonaws.com/default/avatars/male_8.jpg"
                                    onlyFirstWithMeta
                                  >
                                      <Message authorName="Jon Smith" date="21:37" showMetaOnClick>
                                          <MessageMedia>
                                              <img
                                                src="https://static.staging.livechatinc.com/1520/P10B78E30V/dfd1830ebb68b4eefe6432d7ac2be2be/Cat-BusinessSidekick_Wallpapers.png"/>
                                          </MessageMedia>
                                      </Message>
                                      <Message authorName="Jon Smith" date="21:37">
                                          <MessageTitle title="Message title" subtitle="24h"/>
                                          <MessageMedia>
                                              <img
                                                src="https://static.staging.livechatinc.com/1520/P10B78E30V/dfd1830ebb68b4eefe6432d7ac2be2be/Cat-BusinessSidekick_Wallpapers.png"/>
                                          </MessageMedia>
                                          <MessageText style={{background: 'lightgrey', borderRadius: '6px'}}>
                                              The fastest way to help your customers - start chatting with visitors
                                          </MessageText>
                                          <MessageButtons>
                                              <MessageButton label="View more" primary/>
                                              <MessageButton label="Cancel"/>
                                          </MessageButtons>
                                          <MessageText style={{background: 'lightgrey', borderRadius: '6px'}}>
                                              The fastest way to help your customers - start chatting with visitors
                                              who need your help using a free 30-day trial.
                                          </MessageText>
                                          <MessageButtons>
                                              <MessageButton label="View more" primary/>
                                              <MessageButton label="Cancel"/>
                                          </MessageButtons>
                                      </Message>
                                      <Message date="21:38" authorName="Jon Smith">
                                          <MessageText>Hi! I would like to buy those shoes</MessageText>
                                      </Message>
                                  </MessageGroup>
                                  <MessageGroup onlyFirstWithMeta>
                                      <Message date="21:38" isOwn={true} authorName="Visitor">
                                          <MessageText
                                            style={{background: 'lightblue', color: 'white', borderRadius: '6px'}}>
                                              I love them
                                              sooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                              much!
                                          </MessageText>
                                      </Message>
                                      <Message date="21:38" isOwn={true} authorName="Visitor">
                                          <MessageText
                                            style={{background: 'lightblue', color: 'white', borderRadius: '6px'}}>This
                                              helps me a lot</MessageText>
                                      </Message>
                                  </MessageGroup>
                                  <MessageGroup
                                    avatar="https://livechat.s3.amazonaws.com/default/avatars/male_8.jpg"
                                    onlyFirstWithMeta
                                  >
                                      <Message authorName="Jon Smith" date="21:37">
                                          <MessageText style={{background: 'lightgrey', borderRadius: '6px'}}>No
                                              problem!</MessageText>
                                      </Message>
                                      <Message
                                        authorName="Jon Smith"
                                        imageUrl="https://static.staging.livechatinc.com/1520/P10B78E30V/dfd1830ebb68b4eefe6432d7ac2be2be/Cat-BusinessSidekick_Wallpapers.png"
                                        date="21:39"
                                      >
                                          <MessageText style={{background: 'lightgrey', borderRadius: '6px'}}>
                                              The fastest way to help your customers - start chatting with visitors
                                              who need your help using a free 30-day trial.
                                          </MessageText>
                                      </Message>
                                      <Message authorName="Jon Smith" date="21:39">
                                          <MessageMedia>
                                              <img
                                                src="https://static.staging.livechatinc.com/1520/P10B78E30V/dfd1830ebb68b4eefe6432d7ac2be2be/Cat-BusinessSidekick_Wallpapers.png"/>
                                          </MessageMedia>
                                      </Message>
                                  </MessageGroup>
                                  <MessageGroup
                                    avatar="https://livechat.s3.amazonaws.com/default/avatars/male_8.jpg"
                                    onlyFirstWithMeta
                                  >
                                      <Message authorName="Jon Smith" date="21:37">
                                          <MessageText>No problem!</MessageText>
                                      </Message>
                                      <Message
                                        authorName="Jon Smith"
                                        imageUrl="https://static.staging.livechatinc.com/1520/P10B78E30V/dfd1830ebb68b4eefe6432d7ac2be2be/Cat-BusinessSidekick_Wallpapers.png"
                                        date="21:39"
                                      >
                                          <MessageText>
                                              The fastest way to help your customers - start chatting with visitors
                                              who need your help using a free 30-day trial.
                                          </MessageText>
                                      </Message>
                                      <Message authorName="Jon Smith" date="21:39">
                                          <MessageMedia>
                                              <img
                                                src="https://static.staging.livechatinc.com/1520/P10B78E30V/dfd1830ebb68b4eefe6432d7ac2be2be/Cat-BusinessSidekick_Wallpapers.png"/>
                                          </MessageMedia>
                                      </Message>
                                  </MessageGroup>
                                  <MessageGroup
                                    avatar="https://livechat.s3.amazonaws.com/default/avatars/male_8.jpg"
                                    onlyFirstWithMeta
                                  >
                                      <Message authorName="Jon Smith" date="21:37">
                                          <MessageText>No problem!</MessageText>
                                      </Message>
                                      <Message
                                        authorName="Jon Smith"
                                        imageUrl="https://static.staging.livechatinc.com/1520/P10B78E30V/dfd1830ebb68b4eefe6432d7ac2be2be/Cat-BusinessSidekick_Wallpapers.png"
                                        date="21:39"
                                      >
                                          <MessageText>
                                              The fastest way to help your customers - start chatting with visitors
                                              who need your help using a free 30-day trial.
                                          </MessageText>
                                      </Message>
                                      <Message authorName="Jon Smith" date="21:39">
                                          <MessageMedia>
                                              <img
                                                src="https://static.staging.livechatinc.com/1520/P10B78E30V/dfd1830ebb68b4eefe6432d7ac2be2be/Cat-BusinessSidekick_Wallpapers.png"/>
                                          </MessageMedia>
                                      </Message>
                                  </MessageGroup>
                                  <MessageGroup
                                    avatar="https://livechat.s3.amazonaws.com/default/avatars/male_8.jpg"
                                    onlyFirstWithMeta
                                  >
                                      <Message authorName="Jon Smith" date="21:37">
                                          <MessageText>No problem!</MessageText>
                                      </Message>
                                      <Message
                                        authorName="Jon Smith"
                                        imageUrl="https://static.staging.livechatinc.com/1520/P10B78E30V/dfd1830ebb68b4eefe6432d7ac2be2be/Cat-BusinessSidekick_Wallpapers.png"
                                        date="21:39"
                                      >
                                          <MessageText>
                                              The fastest way to help your customers - start chatting with visitors
                                              who need your help using a free 30-day trial.
                                          </MessageText>
                                      </Message>
                                      <Message authorName="Jon Smith" date="21:39">
                                          <MessageMedia>
                                              <img
                                                src="https://static.staging.livechatinc.com/1520/P10B78E30V/dfd1830ebb68b4eefe6432d7ac2be2be/Cat-BusinessSidekick_Wallpapers.png"/>
                                          </MessageMedia>
                                      </Message>
                                  </MessageGroup>
                              </MessageList>
                              <TextComposer onSend={() => null}>
                                  <Row align="center">
                                      <Fill>
                                          <TextInput/>
                                      </Fill>
                                      <Fit>
                                          <SendButton/>
                                      </Fit>
                                  </Row>
                              </TextComposer>
                          </div>
                      </GridItem>
                      <GridItem xs={2}>
                      </GridItem>
                      <GridItem xs={1}>
                      </GridItem>
                      <GridItem xs={4}>
                          <ChatList style={{maxWidth: 300}}>
                              <ChatListItem>
                                  <Avatar letter="K"/>
                                  <Column fill>
                                      <Row justify>
                                          <Title ellipsis>{'Konrad'}</Title>
                                          <Subtitle nowrap>{'14:31 PM'}</Subtitle>
                                      </Row>
                                      <Subtitle ellipsis>
                                          {'Hello, how can I help you? We have a lot to talk about'}
                                      </Subtitle>
                                  </Column>
                              </ChatListItem>
                              <ChatListItem active>
                                  <Avatar letter="J"/>
                                  <Column fill>
                                      <Row justify>
                                          <Title ellipsis>{'Andrew'}</Title>
                                          <Subtitle nowrap>{'14:31 PM'}</Subtitle>
                                      </Row>
                                      <Subtitle ellipsis>{'actually I just emailed you back'}</Subtitle>
                                  </Column>
                              </ChatListItem>
                              <ChatListItem>
                                  <Avatar imgUrl="https://livechat.s3.amazonaws.com/default/avatars/male_8.jpg"/>
                                  <Column fill>
                                      <Row justify>
                                          <Title ellipsis>{'Michael'}</Title>
                                          <Subtitle nowrap>{'14:31 PM'}</Subtitle>
                                      </Row>
                                      <Subtitle ellipsis>
                                          {"Ok, thanks for the details, I'll get back to you tomorrow."}
                                      </Subtitle>
                                  </Column>
                              </ChatListItem>
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