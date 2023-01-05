const { getMessage } = require("../models/messagehandler");
const formatter = require("../utils/responseFormatter");


const getMsg = async (req, res) => {
    let retMsg = "failed";
    let respSuccess = false;
    if (!req.session.userData || !req.session.userData.validated){
        retMsg = "access denied";
        res.json(formatter(respSuccess, retMsg));
        return;
    }

    const userid = req.session.userData.db_id;
    try {
        const result = await getMessage(userid);
        if (result){
            respSuccess = true;
            retMsg = "success";
            res.json(formatter(respSuccess, retMsg, "message", result));
        } else {
            retMsg = "No message exists";
            res.json(formatter(respSuccess, retMsg));
        }
    } catch (error) {
        console.log(error)
    } 
}

module.exports = {getMsg} 