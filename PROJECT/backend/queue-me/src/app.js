const express = require('express');
const morgan = require('morgan');
const helmet = require('helmet');
const cors = require('cors');
const session = require ('express-session');
const bodyparser = require('body-parser');
const randomstring = require('randomstring');

require('dotenv').config();

const sessionSecret = randomstring.generate();
const middlewares = require('./config/middlewares');
const api = require('./api');

const app = express();

app.use(morgan('dev'));
app.use(helmet());
app.use(cors());
app.use(express.json());
app.use(session({
  secret: sessionSecret,
  resave: false,
  saveUninitialized: true,
  cookie: {
    maxAge: 1000*3600
  }
}));

app.use(bodyparser.json());
app.use(bodyparser.urlencoded({ extended: true }))

app.get('/', (req, res) => {
  res.json({
    message: '🦄🌈✨👋🌎🌍🌏✨🌈🦄',
  });
});

app.post('/', (req, res) => {
  console.log(req.body.mango);
  res.json({
    message: '🦄🌈✨👋🌎🌍🌏✨🌈🦄',
  });
});

app.use('/api/v1', api);

app.use(middlewares.notFound);
app.use(middlewares.errorHandler);

module.exports = app;
