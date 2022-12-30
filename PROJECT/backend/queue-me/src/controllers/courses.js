const retrieveCourses = require('../models/coursehandler');
const respFormatter = require('../utils/responseFormatter');

const getCourseList = async (req, res) => {
    let retMsg = "failed";
    let respSuccess = false;
    let clist = [];
    if (!req.session.userData || !req.session.userData.validated){
        retMsg = "access denied";
        res.json(respFormatter(respSuccess, retMsg));
        return;
    }
    
    try {
        const courses = await retrieveCourses();
        retMsg = "success";
        clist = courses.courselist;
        respSuccess = true;
    } catch (err) {
        console.log(err);
        retMsg = "course retrievel failed";
    }
    finally {
        res.json(respFormatter(respSuccess, retMsg, "courselist", clist));
    }
}

module.exports = getCourseList;