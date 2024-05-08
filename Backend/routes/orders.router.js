const express = require('express');
const passport = require('passport');

const OrderService = require('../services/order.service');
const validatorHandler = require('../middlewares/validator.handler');
const { updateOrderSchema, createOrderSchema, getOrderSchema } = require('../schemas/order.schema');

const router = express.Router();
const service = new OrderService();

router.get('/', async (req, res, next) => {
  try {
    const orders = await service.find();
    res.json(orders);
  } catch (error) {
    next(error);
  }
});

router.get('/agricultor/:idAgricultor',   
  passport.authenticate('jwt', {session: false}),
  async (req, res, next) => {
    try {
      const {idAgricultor} = req.params;
      const authenticatedUserId = req.user.sub;
      if(authenticatedUserId == idAgricultor) {
        const orders = await service.findByAgricultor(idAgricultor);
        res.json(orders);}
        else{
          res.status(403).json({ error: "No puedes ver estas ordenes no coincide tu id" })
        }
    } catch (error) {
      next(error);
    }
});

router.get('/client/:idclient',
  passport.authenticate('jwt', {session: false}),   
  async (req, res, next) => {
    try {
      const {idclient} = req.params;
      const authenticatedUserId = req.user.sub;
      if(authenticatedUserId == idclient) {
      const orders = await service.findByClient(idclient);
      res.json(orders);
      }else {
        res.status(403).json({ error: "No puedes ver estas ordenes no coincide tu id" })
      }      
    } catch (error) {
      next(error);
    }
});

router.get('/:id',
  validatorHandler(getOrderSchema, 'params'),
  async (req, res, next) => {
    try {
      const { id } = req.params;
      const order = await service.findOne(id);
      res.json(order);
    } catch (error) {
      next(error);
    }
  }
);

router.post('/',
  passport.authenticate('jwt', {session: false}),
  validatorHandler(createOrderSchema, 'body'),
  async (req, res, next) => {
    try {
      const authenticatedUserId = req.user.sub;
      const body = req.body;
      const clientId = body.clientId;      
      if(authenticatedUserId == clientId) { // Verificar si el usuario autenticado es el mismo que el solicitado
        const newOrder = await service.create(body);
        res.status(201).json(newOrder);
      } else {
        res.status(403).json({ error: "No puedes crear esta orden tu id no coinicde" }); // Devolver un error de permiso
      }
    } catch (error) {
      next(error);
    }
  }
);

router.patch('/:id',
  passport.authenticate('jwt', {session: false}),
  validatorHandler(getOrderSchema, 'params'),
  validatorHandler(updateOrderSchema, 'body'),
  async (req, res, next) => {
    try {
      const authenticatedUserId = req.user.sub;
      const { id } = req.params;
      const body = req.body;
        const order = await service.update(id, body, authenticatedUserId);
        res.json(order);
    } catch (error) {
        next(error);
      }
    }
);

router.delete('/:id',
  passport.authenticate('jwt', {session: false}),
  validatorHandler(getOrderSchema, 'params'),
  async (req, res, next) => {
    try {
      const authenticatedUserId = req.user.sub;
      const { id } = req.params;
      await service.delete(id,authenticatedUserId);
      res.status(201).json({id});
    } catch (error) {
      if (error.message === "No tienes permiso para eliminar esta orden.") {
        res.status(403).json({ error: error.message });
      } else {
        next(error);
      }
    }
  }
);

module.exports = router;

