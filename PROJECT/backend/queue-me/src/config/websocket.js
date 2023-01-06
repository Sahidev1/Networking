const WebSocket = require('ws');

const wss = new WebSocket.Server({port : 8080});

const sockmap = {};

const messageClientSockets = async (sessionID, msg) => {
    const foundSockets = Object.keys(sockmap).filter(key => sockmap[key].sess === sessionID);
    const intervals = [50,250,1000];
    intervals.map((inter) => {
    setTimeout(() => foundSockets.map (key => {
      let client = sockmap[key].socket;
      if (client.readyState === WebSocket.OPEN){
        client.send (msg);
      }
    }),inter)});
  }

const messageSocket = async (socketKey, msg) => {
  const item = sockmap[socketKey];
  if (item){
    const client = item.socket;
    if (client.readyState === WebSocket.OPEN){
      client.send (msg);
    }
  }
}

const broadcastMessage = async (msg) => {
  const foundSockets = Object.keys(sockmap);
  const intervals = [50,250,1000];
  intervals.map((inter) => {
  setTimeout(() => foundSockets.map (key => {
    let client = sockmap[key].socket;
    if (client.readyState === WebSocket.OPEN){
      client.send (msg);
    }
  }),inter)});
}

module.exports = {WebSocket, wss, sockmap, messageClientSockets, broadcastMessage, messageSocket};