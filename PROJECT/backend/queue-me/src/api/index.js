const express = require('express');
const loginAttempt = require ('../controllers/login')
const registerAttempt = require ('../controllers/register')
const getCourseList = require ('../controllers/courses');
const logout = require('../controllers/logout');
const mapSockToSession = require('../controllers/mapSockSession');
const { addItem } = require('../controllers/addQitem');
const getItems = require('../controllers/getQitems');
const { deleteItem } = require('../controllers/deleteQitem');
const { updateItem } = require('../controllers/updateItem');
const { toggleLock } = require('../controllers/toogleClock');
const { putMessage } = require('../controllers/putmessage');
const { getMsg } = require('../controllers/getMessage');

const router = express.Router();

router.get('/courses', getCourseList);

router.get('/getmsg', getMsg);

router.post('/login', loginAttempt);

router.post('/register', registerAttempt);

router.post('/logout', logout);

router.post('/map', mapSockToSession);

router.post('/additem', addItem);

router.post('/updateitem', updateItem);

router.post('/deleteitem', deleteItem);

router.post('/items', getItems);

router.post('/putmsg', putMessage);

router.post('/toggle', toggleLock);


module.exports = router;
