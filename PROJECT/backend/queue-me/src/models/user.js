function User (){
    this.validated = false;
    this.isAdmin = false;
    this.username = null;
    this.db_id = null;
    this.loginAttempStatus = null;
}

module.exports = {User};