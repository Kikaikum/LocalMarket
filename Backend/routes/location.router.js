const express = require('express');
const passport = require('passport');

const UserService = require('./../services/user.service');
const validatorHandler = require('./../middlewares/validator.handler');
const { updateUserSchema, createUserSchema, getUserSchema } = require('./../schemas/user.schema');

const router = express.Router();
const service = new UserService();

router.get('/', async (req, res, next) => {
  try {
    const body = req.body;
    const users = await service.find(body);
    res.json(users);
  } catch (error) {
    next(error);
  }
});



module.exports = router;

