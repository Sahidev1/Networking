const express = require('express');
const loginAttempt = require ('../controllers/login')
const emojis = require('./emojis');

const router = express.Router();

router.get('/', (req, res) => {
  res.json({
    message: 'API - 👋🌎🌍🌏',
  });
});

router.post('/login', async (req, res) => await loginAttempt(req, res));

router.use('/emojis', emojis);

module.exports = router;
