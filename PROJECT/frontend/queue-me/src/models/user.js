import Observer from "./observer";

class User extends Observer {
    constructor (){
        super();
        this.authenticated = false;
        this.username = null;
        this.admin = false;
        this.id = null;
    }

    authenticate (username, admin=false, id){
        this.authenticated = true;
        this.username = username;
        this.admin = admin;
        this.id = id;
        this.notifyObserver();
    }

    deauthenticate (){
        this.authenticate = false;
        this.username = null;
        this.admin = false;
    }

    getProps (){
        return (
            {
                "authenticated": this.authenticated,
                "username": this.username,
                "admin": this.admin,
                "id": this.id
            }
        );
    } 
}

export default User;