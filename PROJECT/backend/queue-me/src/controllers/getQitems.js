const { getQueueItems } = require("../models/queueItemsHandler");
const formatter = require('../utils/responseFormatter');

const getItems = async (req, res) => {
    let itemlist = [];
    let retMsg = "failed";
    let respSuccess = false;
    if (!req.session.userData || !req.session.userData.validated){
        retMsg = "access denied";
        res.json(formatter(respSuccess, retMsg));
        return;
    }   

    const course_id = req.body.course_id;
    try {
        const retProps = await getQueueItems(course_id);
        retMsg = retProps.msg;
        respSuccess = retProps.status;
        if (retProps.status){
            const items = retProps.items;
            itemlist = items.courseQitems;
        }
    } catch (error){
        console.log(error);
    }
    finally {
        res.json(formatter(respSuccess, retMsg, "items", itemlist));
    }
}

module.exports = getItems;