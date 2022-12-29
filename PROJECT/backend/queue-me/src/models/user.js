const bcrypt = require('../utils/hasher')
const dbPool = require('../config/dbconfig')

function User (){
    this.validated = false;
    this.isAdmin = false;
    this.username = null;
    this.db_id = null;
    this.loginAttempStatus = null;
}

User.prototype.login = async function (username, password){
    const userCheck = "SELECT password FROM users " 
        + "WHERE username = '"+ username +"'";
    const loginSuccessQuery = "SELECT id, username, is_admin FROM users " 
    + "WHERE username = '"+ username +"'";

    const client = await dbPool.connect();
    try {
        let res = await client.query(userCheck);
        if (res.rows.length === 1){
            const passHash = res.rows[0].password;
            const match = await bcrypt.compare(password, passHash);
            if (match){
                res = await client.query(loginSuccessQuery);
                this.username = res.rows[0].username;
                this.db_id = res.rows[0].id;
                this.isAdmin = res.rows[0].is_admin;
                this.validated = true;
                this.loginAttempStatus = "success";
            }
            else {
                this.loginAttempStatus = "failed";
            }
        }
        else if (res.rows.length === 0){
            this.loginAttempStatus = "user not found";
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