const { Courses } = require ('../models/courses');
const dbPool = require ('../config/dbconfig');
const { broadcastMessage } = require('../config/websocket');

const retrieveCourses = async function (){
    const retrieveQuery = "SELECT * FROM course";

    const client = await dbPool.connect();
    const courses = new Courses();

    try {
        const res = await client.query(retrieveQuery);
        const tuples = res.rows;
        tuples.forEach((tuple) => {
            courses.addCourse(tuple.id, tuple.title, tuple.is_queue_open);
        });
        
    } catch (error){
        console.log(error);
    }
    finally {
        client.release();
        return courses;
    }
}

//SELECT is_queue_open FROM course WHERE id = 2
// UPDATE course SET is_queue_open = true WHERE ID = 2;


const toggleCourseLock = async function (course_id){
    let status = false;
    let msg = "failed";
    const genUpdateQuery = (course_id, bool) => {
        return "UPDATE course SET is_queue_open =" + bool + " WHERE ID =" + course_id;
    };
    const checkQuery = "SELECT is_queue_open FROM course WHERE id =" + course_id;
    const client = await dbPool.connect();
    let qstate = null;
    let goAhead = false;
    try {
        const res = await client.query(checkQuery);
        if (res.rows.length === 1){
            qstate = res.rows[0].is_queue_open;
            goAhead = true;
        }
        else {
            msg = "invalid course id";
        }
    } catch (error) {
        console.log(error);
    }

    if (goAhead){
        try {
            await client.query('BEGIN');
            await client.query(genUpdateQuery(course_id, !qstate));
            await client.query('COMMIT');
            await broadcastMessage('COURSE_CHANGE');
            status = true;
            msg = "success";
        } catch (err) {
            await client.query('ROLLBACK');
            console.log(err);
        }
    }
    client.release();
    return {status, msg};
}

module.exports = {retrieveCourses, toggleCourseLock}