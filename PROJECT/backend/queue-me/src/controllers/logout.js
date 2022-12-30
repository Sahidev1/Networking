const formatter = require('../utils/responseFormatter');

const logout = async (req, res) => {
    const session = req.session;
    if (session.userData && session.userData.validated){
        session.userData = null;
        res.json(formatter(true, "logged out"));
    }
    else {
        res.json(formatter(false, "already logged out"));
    }
}

module.exports = logout;