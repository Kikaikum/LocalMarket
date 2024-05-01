const boom = require('@hapi/boom');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

const { models } = require('./../libs/sequelize');
const { config } = require('./../config/config');

class UserService {
  constructor() {}

  async find(location) {
    const agricultor = await models.User.findAll({
      where: { location }
    });
    return agricultor;
  }

}

module.exports = UserService;
