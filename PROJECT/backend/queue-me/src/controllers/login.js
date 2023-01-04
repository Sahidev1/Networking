const userHandler  = require('../models/userhandler');
const formatter = require('../utils/responseFormatter');
const { messageClientSockets} = require('../config/websocket');

const loginAttempt = async (req, res) => {
    const credentials = req.body;
    let loginsuccess = false;
    let loginstatusData = "failed";
    let userdata = null;
    if (req.session.userData && req.session.userData.validated){
        loginstatusData = "User already logged in";
        res.json(formatter(loginsuccess, loginstatusData));
        return;
    }
    try {
        const user = await userHandler.login(credentials.username, credentials.password);
        if (user.validated){
          req.session.userData = user;
          userdata = {username: user.username, adminstatus: user.isAdmin, id: user.db_id};
          loginsuccess = true;
      
        } else {
          req.session.userData = null;
        }
        loginstatusData = user.loginAttempStatus;
    } catch (error) {
      console.log(error);
    }
    finally {
      res.json(formatter(loginsuccess, loginstatusData, "user", userdata));
      if (loginsuccess) await messageClientSockets(req.sessionID, "AUTH_CHANGED");
    }
}

module.exports = loginAttempt;