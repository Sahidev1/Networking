const { addQueueItem } = require("../models/queueItemsHandler");
const formatter = require('../utils/responseFormatter');

const addItem = async (req, res) => {
    let retMsg = "failed";
    let respSuccess = false;
    if (!req.session.userData || !req.session.userData.validated){
        retMsg = "access denied";
        res.json(formatter(respSuccess, retMsg));
        return;
    }
    const qItem = req.body;
    if (req.session.userData.db_id != qItem.user_id){
        retMsg = "access denied";
        res.json(formatter(respSuccess, retMsg));
        return;
    }

    try {
        const addStatus = await addQueueItem(qItem.user_id, qItem.course_id, qItem.location, qItem.comment);
        respSuccess = addStatus.status;
        retMsg = addStatus.msg;
    } catch (error) {
        console.log(error);
    }
    finally {
        res.json(formatter(respSuccess, retMsg));
    }
}

module.exports = {addItem}