const express = require('express');
const loginAttempt = require ('../controllers/login')
const emojis = require('./emojis');

const router = express.Router();

router.get('/', (req, res) => {
  res.json({
    message: 'API - 👋🌎🌍🌏',
  });
});

router.post('/login', async (req, res) => {
  credentials = req.body;
  let loginstatusData = "failed";
  console.log("login called");
  try {
      const user = await loginAttempt(credentials.username, credentials.password);
      if (user.validated){
        console.log("login success");
        req.session.userData = user;
      } else {
        console.log("login failed due to: " + user.loginAttempStatus);
        req.session.userData = null;
      }
      loginstatusData = user.loginAttempStatus;
  } catch (error) {
    console.log(error);
  }
  finally {
    res.json({
      loginstatus: loginstatusData
    });
  }
});

router.use('/emojis', emojis);

module.exports = router;
