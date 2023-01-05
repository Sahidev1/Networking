const express = require('express');
const loginAttempt = require ('../controllers/login')
const registerAttempt = require ('../controllers/register')
const getCourseList = require ('../controllers/courses');
const emojis = require('./emojis');
const logout = require('../controllers/logout');
const mapSockToSession = require('../controllers/mapSockSession');
const { addItem } = require('../controllers/addQitem');
const getItems = require('../controllers/getQitems');
const { deleteItem } = require('../controllers/deleteQitem');
const { updateItem } = require('../controllers/updateItem');

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

router.post('/additem', addItem);

router.post('/updateitem', updateItem);

router.post('/deleteitem', deleteItem);

router.post('/items', getItems);

router.use('/emojis', emojis);


module.exports = router;
