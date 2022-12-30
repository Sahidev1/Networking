const userHandler = require('../models/userhandler');
const formatter = require('../utils/responseFormatter');

const registerAttempt = async (req, res) => {
    const credentials = req.body;
    let registerstatusData = "register failed";
    let registersuccess = false;
    if (req.session.userData && req.session.userData.validated){
        res.json(formatter(false, "already logged in"));
        return;
    }
    try {
        const regStatus = await userHandler.register(credentials.username, credentials.password);
        registerstatusData = regStatus.registerStatusMsg;
        registersuccess = regStatus.registerSucceded;
    } catch (error) {
        console.log(error);
    }
    finally {
        res.json(formatter(registersuccess, registerstatusData));
    }
}

module.exports = registerAttempt;