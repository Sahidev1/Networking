const userHandler  = require('../models/userhandler');

const loginAttempt = async (req, res) => {
    const credentials = req.body;
    let loginstatusData = "failed";
    if (req.session.userData && req.session.userData.validated){
        loginstatusData = "User already logged in";
        res.json({loginstatus: loginstatusData});
        return;
    }
    try {
        const user = await userHandler.login(credentials.username, credentials.password);
        if (user.validated){
          req.session.userData = user;
        } else {
          req.session.userData = null;
        }
        loginstatusData = user.loginAttempStatus;
    } catch (error) {
      console.log(error);
    }
    finally {
      res.json({
        loginstatus: loginstatusData
      });
    }
}

module.exports = loginAttempt;