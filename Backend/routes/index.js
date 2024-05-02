const express = require('express');

const usersRouter = require('./users.router');
const productsRouter = require('./products.router');
const ordersRouter = require('./orders.router');
const locationRouter = require('./location.router');

function routerApi(app) {
  const router = express.Router();
  app.use('/localmarket/v1', router);
  
  router.use('/users', usersRouter);
  router.use('/products', productsRouter);
  router.use('/orders', ordersRouter);
  router.use('/location', locationRouter);

}

module.exports = routerApi;
