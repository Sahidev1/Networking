const { messageClientSockets } = require('../config/websocket');
const formatter = require('../utils/responseFormatter');


const logout = async (req, res) => {
    const session = req.session;
    if (session.userData && session.userData.validated){
        session.userData = null;
        res.json(formatter(true, "logged out"));
        await messageClientSockets(req.sessionID, "AUTH_CHANGED");
    }
    else {
        res.json(formatter(false, "already logged out"));
    }
}

module.exports = logout;