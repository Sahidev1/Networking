import Observer from "./observer";

class User extends Observer {
    constructor (){
        super();
        this.authenticated = false;
        this.username = null;
        this.admin = false;
    }

    authenticate (username, admin=false){
        this.authenticated = true;
        this.username = username;
        this.admin = admin;
    }

    deauthenticate (){
        this.authenticate = false;
        this.username = null;
        this.admin = false;
    }
}

