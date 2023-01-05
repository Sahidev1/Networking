const dbPool = require("../config/dbconfig")

//INSERT INTO messages VALUES (DEFAULT, 1, 2, '2023-01-02 11:56:03','wowza');
//UPDATE messages SET from_id = 1, timeof='2023-01-03 23:44:01', content='damn' WHERE to_id = 2;
//SELECT * FROM messages WHERE to_id = 3;

const putMessageIntoDb = async (from_id, to_id, timeof, content) => {
    const checkIfEntryExists = "SELECT * FROM messages WHERE to_id =" + to_id;
    const genInsertQuery = (from_id, to_id, timeof, content) => {
        return "INSERT INTO messages VALUES (DEFAULT, "+ from_id +", "+ to_id +", '" + timeof + "','"+ content +"')";
    }
    const genUpdateQuery = (from_id, timeof, content, to_id) => {
        return "UPDATE messages SET from_id = "+ from_id +", timeof='" + timeof + "', content='"+ content + "' WHERE to_id=" + to_id;
    }

    let status = false;
    let msg = "failed";

    let goAhead = false;
    let doInsert = false;
    const client = await dbPool.connect();
    try {
        const res = await client.query(checkIfEntryExists);
        if (res.rows.length === 1){
            goAhead = true;
        } 
        else if (res.rows.length === 0){
            goAhead = true;
            doInsert = true;
        } else {
            throw "Error: multiple db entries for user id";
        }
    } catch (error) {
        console.log(error)
    }

    if (!goAhead){
        client.release();
        return {status, msg};
    }

    try {
        await client.query('BEGIN');
        if (doInsert){
            await client.query(genInsertQuery(from_id, to_id, timeof, content));
        } 
        else {
            await client.query(genUpdateQuery(from_id, timeof, content, to_id));
        }
        await client.query('COMMIT');
        status = true;
        msg = "succesfull";
    } catch (error) {
        await client.query('ROLLBACK');
        console.log(error);
    }
    client.release();
    return {status, msg};
}

//DELETE FROM messages WHERE to_id = 2;
//SELECT * FROM messages WHERE to_id = 2;
//SELECT messages.timeof, messages.content, users.username FROM messages, users WHERE users.id = messages.from_id AND messages.to_id = 2;
const getMessage = async (userid) => {
    let data = null;

    const query = "SELECT messages.timeof, messages.content, users.username FROM messages, users WHERE users.id = messages.from_id AND messages.to_id =" + userid;

    const client = await dbPool.connect();
    try {
        const res = await client.query(query);
        if (res.rows.length === 1){
            const val = res.rows[0];
            data = {"from": val.username, "time": val.timeof, "content": val.content};
        }
    } catch (error) {
        console.log(error);
    }

    const deleteQuery = "DELETE FROM messages WHERE to_id =" + userid;
    if (data){
        try {
            await client.query('BEGIN');
            await client.query(deleteQuery);
            await client.query('COMMIT');
        } catch (error) {
            await client.query('ROLLBACK');
            console.log(error)
        }
    }

    client.release();
    return data;
}


module.exports = {putMessageIntoDb, getMessage};