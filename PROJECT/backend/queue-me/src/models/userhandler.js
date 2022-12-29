const {User} = require ('../models/user')
const bcrypt = require('../utils/hasher')
const dbPool = require('../config/dbconfig')


const login = async function (username, password){
    const userCheck = "SELECT password FROM users " 
        + "WHERE username = '"+ username +"'";
    const loginSuccessQuery = "SELECT id, username, is_admin FROM users " 
    + "WHERE username = '"+ username +"'";
    const client = await dbPool.connect();
    const user = new User ();
    try {
        let res = await client.query(userCheck);
        if (res.rows.length === 1){
            const passHash = res.rows[0].password;
            const match = await bcrypt.compare(password, passHash);
            if (match){
                res = await client.query(loginSuccessQuery);
                user.username = res.rows[0].username;
                user.db_id = res.rows[0].id;
                user.isAdmin = res.rows[0].is_admin;
                user.validated = true;
                user.loginAttempStatus = "success";
            }
            else {
                user.loginAttempStatus = "failed";
            }
        }
        else if (res.rows.length === 0){
            user.loginAttempStatus = "user not found";
        }
        else {
            user.loginAttempStatus = "failed";
            console.log("login function unexpected behavior");
        }
    } catch (error) {
        console.log(error)
    } finally {
        client.release();
        return user;
    }
}
/*INSERT INTO users VALUES (DEFAULT, 'zango',
 DEFAULT, '')*/

const register = async function (username, password){
    const usernameCheck = "SELECT * FROM users WHERE username = '" + username + "'";

    const genInsertQuery = (username, hash) => {
        return "INSERT INTO users VALUES (DEFAULT, '"+ username +"'," +
        "DEFAULT" + ", '" + hash +"')";
    }

    const saltRounds = 5;
    let registerStatusMsg = "failed";
    let registerSucceded = false;
    const client = await dbPool.connect();
    try {
        await client.query('BEGIN');
        let res = await client.query(usernameCheck);
        if (res.rows.length === 1){
            registerStatusMsg = "Username already exists";
        } 
        if (res.rows.length === 0){
            const passwordHash = await bcrypt.hash(password, saltRounds);
            const insertQuery = genInsertQuery (username, passwordHash);
            await client.query (insertQuery);
            await client.query ('COMMIT');
            registerStatusMsg = "registation success";
            registerSucceded = true;
        }
    } catch (error) {
        await client.query('ROLLBACK');
        console.log(error);
    } finally {
        client.release();
        return {registerSucceded, registerStatusMsg};
    }
}

module.exports = {login, register}