const { updateQueueItem } = require("../models/queueItemsHandler");
const formatter = require("../utils/responseFormatter");




const updateItem = async (req, res) => {
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
        const updateStatus = await updateQueueItem(qItem.user_id, qItem.location, qItem.comment);
        respSuccess = updateStatus.status;
        retMsg = updateStatus.msg;
    } catch (error) {
        console.log(error);
    }
    finally {
        res.json(formatter(respSuccess, retMsg));
    }
}

module.exports = {updateItem}