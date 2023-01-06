const {sockmap} = require('../config/websocket')
const formatter = require('../utils/responseFormatter');

const mapSockToSession = async (req, res) => {
    const msg = req.body;
    const sessionID = req.sessionID;
    
    const filtered = Object.keys(sockmap).filter(key => sockmap[key].sess === sessionID);
    if (filtered[0]){
        delete sockmap[filtered[0]];
    }

    if (msg?.wskey){
        sockmap[msg.wskey].sess = sessionID;
        sockmap[msg.wskey].userData = req.session.userData;
        res.json(formatter(true, "socket mapped to session"));
    }
    else {
        res.json(formatter(false, "mapping socket and session failed"));
    }
}

module.exports = mapSockToSession;