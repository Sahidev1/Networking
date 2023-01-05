const { toggleCourseLock } = require("../models/coursehandler");
const formatResponse = require("../utils/responseFormatter");

const toggleLock = async (req, res) => {
    let retMsg = "failed";
    let respSuccess = false;
    if (!req.session.userData || !req.session.userData.validated || !req.session.userData.isAdmin){
        retMsg = "access denied";
        res.json(formatResponse(respSuccess, retMsg));
        return;
    }
    const course_id = req.body.course_id;

    try {
        const resdata = await toggleCourseLock (course_id);
        respSuccess = resdata.status;
        retMsg = resdata.msg;
    } catch (error) {
        console.log(error);
    }
    res.json(formatResponse(respSuccess, retMsg));
}

module.exports = {toggleLock};