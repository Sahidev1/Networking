
const dbPool = require('../config/dbconfig')

function User (){
    this.validated = false;
    this.isAdmin = false;
    this.username = null;
    this.db_id = null;
    this.loginAttempStatus = null;
}

User.prototype.login = async function (username, password){
    const loginQuery = "SELECT * FROM users " 
        + "WHERE username = '"+ username +"' AND password = '" + password + "'";
    const client = await dbPool.connect();
    try {
        const res = await client.query(loginQuery);
        if (res.rows.length === 1){
            this.username = res.rows[0].username;
            this.db_id = res.rows[0].id;
            this.isAdmin = res.rows[0].is_admin;
            this.validated = true;
            this.loginAttempStatus = "success";
        }
        else if (res.rows.length === 0){
            this.loginAttempStatus = "failed";
        }
        else {
            this.loginAttempStatus = "failed";
            console.log("login function unexpected behavior");
        }
    } catch (error) {
        console.log(error)
    } finally {
        client.release();
    }
}

module.exports = {User}