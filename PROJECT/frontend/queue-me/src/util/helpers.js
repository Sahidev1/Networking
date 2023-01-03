
import User from "../models/user";

export function checkLocalForAuth (){
    const localObj = JSON.parse(localStorage.getItem('user'));
    const user = new User();

    if(localObj) {
        user.authenticate(localObj.username, localObj.admin);
    }
    return user;
};

export function checkAuth (){
    return JSON.parse(localStorage.getItem('user')) !== null;
}
