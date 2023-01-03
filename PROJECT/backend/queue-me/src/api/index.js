const express = require('express');
const loginAttempt = require ('../controllers/login')
const registerAttempt = require ('../controllers/register')
const getCourseList = require ('../controllers/courses');
const emojis = require('./emojis');
const logout = require('../controllers/logout');
const mapSockToSession = require('../controllers/mapSockSession');

const router = express.Router();

router.get('/', (req, res) => {
  res.json({
    message: 'API - 👋🌎🌍🌏',
  });
});

router.get('/courses', getCourseList);

router.post('/login', loginAttempt);

router.post('/register', registerAttempt);

router.post('/logout', logout);

router.post('/map', mapSockToSession);

router.use('/emojis', emojis);


module.exports = router;
