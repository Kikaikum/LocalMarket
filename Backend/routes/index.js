const express = require('express');

const usersRouter = require('./users.router');
const productsRouter = require('./products.router');

function routerApi(app) {
  const router = express.Router();
  app.use('/localmarket/v1', router);
  
  router.use('/users', usersRouter);
  router.use('/products', productsRouter);

}

module.exports = routerApi;
