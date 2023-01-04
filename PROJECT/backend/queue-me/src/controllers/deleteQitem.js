const { deleteQitem, getItemUserid } = require("../models/queueItemsHandler");
const formatResponse = require("../utils/responseFormatter");


const deleteItem = async (req, res) => {
    let retMsg = "failed";
    let respSuccess = false;
    if (!req.session.userData || !req.session.userData.validated){
        retMsg = "access denied";
        res.json(formatResponse(respSuccess, retMsg));
        return;
    }

    const body = req.body;
    const userid = await getItemUserid (body.item_id);
    const userData = req.session.userData;
    if (userid !== userData.db_id && !userData.isAdmin){
        retMsg = "access denied";
        res.json(formatResponse(respSuccess, retMsg));
        return;
    }

    try {
        const res = await deleteQitem(body.item_id);
        respSuccess = res.status;
        retMsg = res.msg;
    } catch (error) {
        console.log(error);
    }
    finally {
        res.json(formatResponse(respSuccess, retMsg));
    }
}

module.exports = {deleteItem};