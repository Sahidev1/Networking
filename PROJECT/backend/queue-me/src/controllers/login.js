const { User } = require('../models/user');

const loginAttempt = async (req, res) => {
    const loginfun = async (username, password) => {
        const user = new User();
        try {
            await user.login(username, password);
        } catch (error) {
            console.log(error);
        }  
        finally {
            return user;
        }
    }

    const credentials = req.body;
    let loginstatusData = "failed";
    try {
        const user = await loginfun(credentials.username, credentials.password);
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