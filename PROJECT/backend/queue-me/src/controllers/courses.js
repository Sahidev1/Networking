const retrieveCourses = require('../models/coursehandler');

const getCourseList = async (req, res) => {
    let retrievalStatus = "failed";
    let clist = [];
    if (!req.session.userData || !req.session.userData.validated){
        retrievalStatus = "access denied";
        res.json({status: retrievalStatus})
        return;
    }
    
    try {
        const courses = await retrieveCourses();
        retrievalStatus = "success";
        clist = courses.courselist;
    } catch (err) {
        console.log(err);
    }
    finally {
        res.json([{status: retrievalStatus},...clist]);
    }
}

module.exports = getCourseList;