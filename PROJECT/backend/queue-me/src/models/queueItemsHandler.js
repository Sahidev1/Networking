const dbPool = require("../config/dbconfig");
const { broadcastMessage } = require("../config/websocket");
const { QueueItem } = require("./queueItem");
const { QueueItems } = require("./queueItems");

const isQopenQuery = (c_id) => {
    return "SELECT is_queue_open FROM course WHERE id=" + c_id; 
}

//SELECT queue_item.*, users.username FROM queue_item, users WHERE users.id = queue_item.userid AND queue_item.course_id = 1;
//SELECT * FROM queue_item WHERE course_id = 1;
const getQueueItems = async (course_id) => {
    const getQitemsQuery = (course_id) => {
        return "SELECT queue_item.*, users.username FROM queue_item, users WHERE users.id = queue_item.userid AND queue_item.course_id=" + course_id;
    } 
    let queryGoAhead = false;
    const items = new QueueItems (course_id);
    let status = false;
    let msg = "fail to get items";
    const client = await dbPool.connect();
    try {
        let res = await client.query(isQopenQuery(course_id));
        if (res.rows.length === 0){
            msg = "course does not exist";
        } 
        else if (res.rows[0].is_queue_open){
            queryGoAhead = true;
        }
        else {
            msg = "course is locked";
        }

        if(queryGoAhead){
            res = await client.query(getQitemsQuery(course_id));
            const tuples = res.rows;
            tuples.forEach ((tuple) => {
                items.addItem(tuple.id, tuple.userid, tuple.place, tuple.comment, tuple.username);
            });
            status = true;
            msg = "success";
        }
    } catch (error) {
        console.log(error);
    }
    finally {
        client.release();
        return {status, msg, items};
    }
}

//SELECT is_queue_open FROM course WHERE id=2;
//INSERT INTO queue_item VALUES (DEFAULT, 2, 1, 'zoom.com/dfspdokfp','present')
//SELECT * FROM queue_item WHERE userid = 2 AND course_id = 2

const addQueueItem = async function (user_id, course_id, location, comment){
    let status = false;
    let msg = "adding item failed";
    const itemAlreadyExistsQuery = (user_id) => {
        return "SELECT * FROM queue_item WHERE userid =" + user_id;
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
            let resExists = await client.query(itemAlreadyExistsQuery(user_id));
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
            status = true;
            msg = "succesfully added item";
            await broadcastMessage('QUEUE_CHANGE');
        } catch (err) {
            await client.query('ROLLBACK');
            console.log(err);
        }
    }
    client.release();
    return {status, msg};
}

//DELETE FROM queue_item WHERE id = 34;

const deleteQitem = async (item_id) => {
    const genDeleteQuery = (id) => {
        return "DELETE FROM queue_item WHERE id =" + id;
    }
    let status = false;
    let msg = "failed";
    const client = await dbPool.connect();
    try {
        await client.query('BEGIN');
        await client.query(genDeleteQuery(item_id));
        await client.query('COMMIT');
        status = true;
        msg = "Delete successfull";
        await broadcastMessage('QUEUE_CHANGE');
    } catch (error) {
        await client.query('ROLLBACK');
        msg = "Delete failed";
    }
    finally {
        client.release();
        return {status, msg};
    }
}

//SELECT userid  FROM queue_item WHERE id = 6;

const getItemUserid = async (item_id) => {
    const getUserIDQuery = (item_id) => {
        return "SELECT userid  FROM queue_item WHERE id =" + item_id;
    }
    let retval = null;
    const client = await dbPool.connect();
    try {
        const res = await client.query(getUserIDQuery(item_id));
        if (res.rows.length > 0){
            retval = res.rows[0].userid;
        }
    } catch (err) {
        console.log(err);
    } finally {
        client.release();
    }
    return retval;
}

module.exports = {addQueueItem, getQueueItems, deleteQitem, getItemUserid}