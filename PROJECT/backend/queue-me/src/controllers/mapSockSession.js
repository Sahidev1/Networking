const {sockmap} = require('../config/websocket')
const formatter = require('../utils/responseFormatter');

const mapSockToSession = async (req, res) => {
    const msg = req.body;
    const sessionID = req.sessionID;
    console.log(JSON.stringify(req.body));
    console.log(msg?.wskey);
    Object.keys(sockmap).map(k => console.log('key:' + k));

    if (msg?.wskey){
        sockmap[msg.wskey].sess = sessionID;
        res.json(formatter(true, "socket mapped to session"));
    }
    else {
        res.json(formatter(false, "mapping socket and session failed"));
    }
}

module.exports = mapSockToSession;