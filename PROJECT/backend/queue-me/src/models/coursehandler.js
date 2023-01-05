const { Courses } = require ('../models/courses');
const dbPool = require ('../config/dbconfig');

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



module.exports = retrieveCourses;