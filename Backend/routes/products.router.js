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

router.get('/agricultor/:idAgricultor',   
  async (req, res, next) => {
    try {
      const {idAgricultor} = req.params;
      const products = await service.findByAgricultor(idAgricultor);
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
  passport.authenticate('jwt', {session: false}),
  validatorHandler(createProductSchema, 'body'),
  async (req, res, next) => {
    try {
      const authenticatedUserId = req.user.sub;
      const body = req.body;
      const idAgricultor = body.idAgricultor;
      if(authenticatedUserId == idAgricultor) { // Verificar si el usuario autenticado es el mismo que el solicitado
        const newProduct = await service.create(body);
        res.status(201).json(newProduct);
      } else {
        res.status(403).json({ error: "No puedes crear un producto para este ID de agricultor" }); // Devolver un error de permiso
      }
    } catch (error) {
      next(error);
    }
  }
);

router.patch('/:id',
  passport.authenticate('jwt', {session: false}),
  validatorHandler(getProductSchema, 'params'),
  validatorHandler(updateProductSchema, 'body'),
  async (req, res, next) => {
    try {
      const authenticatedUserId = req.user.sub;
      const { id } = req.params;
      const body = req.body;
      const product = await service.update(id, body, authenticatedUserId);
      res.json(product);
    } catch (error) {
      if (error.message === "No tienes permiso para actualizar este producto.") {
        res.status(403).json({ error: error.message });
      } else {
        next(error);
      }
    }
  }
);


router.delete('/:id',
  passport.authenticate('jwt', {session: false}),
  validatorHandler(getProductSchema, 'params'),
  async (req, res, next) => {
    try {
      const authenticatedUserId = req.user.sub;
      const { id } = req.params;
      await service.delete(id,authenticatedUserId);
      res.status(201).json({id});
    } catch (error) {
      if (error.message === "No tienes permiso para eliminar este producto.") {
        res.status(403).json({ error: error.message });
      } else {
        next(error);
      }
    }
  }
);

module.exports = router;

