const express = require('express');
const loginAttempt = require ('../controllers/login')
const registerAttempt = require ('../controllers/register')
const emojis = require('./emojis');

const router = express.Router();

router.get('/', (req, res) => {
  res.json({
    message: 'API - 👋🌎🌍🌏',
  });
});

router.post('/login', async (req, res) => await loginAttempt(req, res));

router.post('/register', async (req, res) => await registerAttempt(req, res));

router.use('/emojis', emojis);

module.exports = router;
