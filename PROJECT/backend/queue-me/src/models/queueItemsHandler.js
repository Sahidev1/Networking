const dbPool = require("../config/dbconfig");
const { QueueItem } = require("./queueItem");

//SELECT is_queue_open FROM course WHERE id=2;
//INSERT INTO queue_item VALUES (DEFAULT, 2, 1, 'zoom.com/dfspdokfp','present')
//SELECT * FROM queue_item WHERE userid = 2 AND course_id = 2


const addQueueItem = async function (user_id, course_id, location, comment){
    const item = new QueueItem(user_id, course_id, location, comment);
    let status = "failed";
    let msg = "adding item failed";
    const isQopenQuery = (c_id) => {
        return "SELECT is_queue_open FROM course WHERE id=" + c_id; 
    }
    const itemAlreadyExistsQuery = (user_id, course_id) => {
        return "SELECT * FROM queue_item WHERE userid =" + user_id + " AND course_id =" + course_id;
    }
    const insertQueryGen = (user_id, course_id, location, comment) => {
        return "INSERT INTO queue_item VALUES (DEFAULT," + user_id +", "+ course_id +", '"+ location +"','"+ comment +"')";
    }
    let insertGoAhead = false;
    const client = await dbPool.connect();
    try {
        let resQstatus = await client.query(isQopenQuery(course_id));
        if (resQstatus.rows.length === 0){
            msg = "Course does not exist";
        }
        else if (resQstatus.rows[0].is_queue_open){
            let resExists = await client.query(itemAlreadyExistsQuery(user_id, course_id));
            if (resExists.rows.length === 0) insertGoAhead = true;
            else msg = "User is already queued";
        } 
        else {
            msg = "Course is locked";
        }
    } catch (err){
        console.log(err);
    }

    if (insertGoAhead){
        try {
            await client.query('BEGIN');
            await client.query (insertQueryGen(user_id, course_id, location, comment));
            await client.query('COMMIT');
            status = "success";
            msg = "succesfully added item";
        } catch (err) {
            await client.query('ROLLBACK');
            console.log(err);
        }
    }
    client.release();
    return {status, msg};
}

module.exports = {addQueueItem}