const express = require('express');
const passport = require('passport');

const UserService = require('./../services/user.service');
const validatorHandler = require('./../middlewares/validator.handler');
const { updateUserSchema, createUserSchema, getUserSchema } = require('./../schemas/user.schema');

const router = express.Router();
const service = new UserService();

router.get('/', async (req, res, next) => {
  try {
    const users = await service.find();
    res.json(users);
  } catch (error) {
    next(error);
  }
});

router.get('/username/:username',
  async (req, res, next) => {
    try {
      const { username } = req.params;
      const user = await service.findByUsernamePublic(username);
      res.json(user);
    } catch (error) {
      next(error);
    }
  }
);

router.get('/:id',
  validatorHandler(getUserSchema, 'params'),
  async (req, res, next) => {
    try {
      const { id } = req.params;
      const user = await service.findOne(id);
      res.json(user);
    } catch (error) {
      next(error);
    }
  }
);

router.post('/',
  validatorHandler(createUserSchema, 'body'),
  async (req, res, next) => {
    try {
      const body = req.body;
      const newUser = await service.create(body);
      res.status(201).json(newUser);
    } catch (error) {
      next(error);
    }
  }
);

router.patch('/:id',
  passport.authenticate('jwt', {session: false}),
  validatorHandler(getUserSchema, 'params'),
  validatorHandler(updateUserSchema, 'body'),
  async (req, res, next) => {
    try {
      const id = req.user.sub; // Obtener el ID del usuario autenticado
      const requestedUserId = req.params.id;
      const body = req.body;
      if(id == requestedUserId) { // Verificar si el usuario autenticado es el mismo que el solicitado
        const user = await service.update(id, body);
        res.json(user);
      } else {
        res.status(403).json({ error: "No tienes permiso para modificar este usuario" }); // Devolver un error de permiso
      }
    } catch (error) {
      next(error);
    }
  }
);

router.post('/login',
  passport.authenticate('local', {session: false}),
  async (req, res, next) => {
    try {
      const user = req.user;
      res.json(service.signToken(user));
    } catch (error) {
      next(error);
    }
  }
);

router.delete('/:id',
  passport.authenticate('jwt', {session: false}),
  validatorHandler(getUserSchema, 'params'),
  async (req, res, next) => {
    try {
      const authenticatedUserId = req.user.sub; // Obtener el ID del usuario autenticado
      const requestedUserId = req.params.id; // Obtener el ID del usuario solicitado a eliminar   
      if(authenticatedUserId == requestedUserId) { // Verificar si el usuario autenticado es el mismo que el solicitado
        await service.delete(requestedUserId); // Eliminar el recurso
        res.status(201).json({ id: requestedUserId });
      } else {
        res.status(403).json({ error: "No tienes permiso para eliminar este recurso." }); // Devolver un error de permiso
      }
      
    } catch (error) {
      next(error);
    }
  }
);

module.exports = router;

