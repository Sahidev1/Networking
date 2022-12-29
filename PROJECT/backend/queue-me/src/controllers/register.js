const userHandler = require('../models/userhandler');

const registerAttempt = async (req, res) => {
    const credentials = req.body;
    let registerstatusData = "failed";
    if (req.session.userData && req.session.userData.validated){
        registerstatusData = "User logged in";
        res.json({registerstatus: registerstatusData});
        return;
    }
    try {
        const regStatus = await userHandler.register(credentials.username, credentials.password);
        if (regStatus.registerSucceded){
            registerstatusData = regStatus.registerStatusMsg;
        }
    } catch (error) {
        console.log(error);
    }
    finally {
        res.json({
            registerstatus: registerstatusData
        });
    }
}

module.exports = registerAttempt;