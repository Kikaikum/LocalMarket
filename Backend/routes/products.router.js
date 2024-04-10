const express = require('express');
const passport = require('passport');

const ProductService = require('../services/product.service');
const validatorHandler = require('../middlewares/validator.handler');
const { updateProductSchema, createProductSchema, getProductSchema } = require('../schemas/product.schema');

const router = express.Router();
const service = new ProductService();

router.get('/', async (req, res, next) => {
  try {
    const products = await service.find();
    res.json(products);
  } catch (error) {
    next(error);
  }
});

router.get('/:idAgricultor', 
  async (req, res, next) => {
    try {
      const {agricultor} = req.params;
      const products = await service.findByAgricultor(agricultor);
      res.json(products);
    } catch (error) {
      next(error);
    }
});


router.get('/:id',
  validatorHandler(getProductSchema, 'params'),
  async (req, res, next) => {
    try {
      const { id } = req.params;
      const product = await service.findOne(id);
      res.json(product);
    } catch (error) {
      next(error);
    }
  }
);

router.post('/',
  validatorHandler(createProductSchema, 'body'),
  async (req, res, next) => {
    try {
      const body = req.body;
      const newProduct = await service.create(body);
      res.status(201).json(newProduct);
    } catch (error) {
      next(error);
    }
  }
);

router.patch('/:id',
  //passport.authenticate('jwt', {session: false}),
  validatorHandler(getProductSchema, 'params'),
  validatorHandler(updateProductSchema, 'body'),
  async (req, res, next) => {
    try {
      const { id } = req.params;
      const body = req.body;
      const product = await service.update(id, body);
      res.json(product);
    } catch (error) {
      next(error);
    }
  }
);


router.delete('/:id',
  passport.authenticate('jwt', {session: false}),
  validatorHandler(getProductSchema, 'params'),
  async (req, res, next) => {
    try {
      const { id } = req.params;
      await service.delete(id);
      res.status(201).json({id});
    } catch (error) {
      next(error);
    }
  }
);

module.exports = router;

