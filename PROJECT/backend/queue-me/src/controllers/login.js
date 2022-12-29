const { User } = require('../models/user');

const loginAttempt = async (username, password)=>{
    const user = new User();
    try {
        await user.login(username, password);
    } catch (error) {
        console.log(error);
    }  
    finally {
        return user;
    }
}

module.exports = loginAttempt;