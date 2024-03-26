const boom = require('@hapi/boom');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

const { models } = require('./../libs/sequelize');
const { config } = require('./../config/config');

class UserService {
  constructor() {}

  async create(data) {
    const hash = await bcrypt.hash(data.password, 10);
    const newUser = await models.User.create({
      ...data,
      password: hash
    });
    delete newUser.dataValues.password;
    return newUser;
  }

  async find() {
    const users = await models.User.findAll();
    users.forEach((usuario) => {
      delete usuario.dataValues.password;
    });
    return users;
  }

  async findOne(id) {
    const user = await models.User.findByPk(id);
    if (!user) {
      throw boom.notFound('user not found');
    }
    delete user.dataValues.password;
    return user;
  }

  async findByUsername(username) {
    const user = await models.User.findOne({
      where: { username }
    });
    //delete rta.dataValues.password;
    //delete rta.dataValues.token;
    return user;
  }

  async update(id, changes) {
    const user = await this.findOne(id);
    if (chenges.password){
      const hash = await bcrypt.hash(changes.password, 10);
      const rta = await user.update({
        ...changes,
        password: hash});
      delete rta.dataValues.password;
    }
    else{
      const rta = await user.update(changes);
      delete rta.dataValues.password;
    }
    
    return rta;
  }

  async delete(id) {
    const user = await this.findOne(id);
    await user.destroy();
    return { id };
  }

  async getUser(username, password) {
    const user = await this.findByUsername(username);
    if (!user) {
      throw boom.unauthorized();
    }
    const isMatch = await bcrypt.compare(password, user.password);
    if (!isMatch) {
      throw boom.unauthorized();;
    }
    delete user.dataValues.password;
    return user;
  }

  signToken(user) {
    const payload = {
      sub: user.id,
      role: user.username
    }
    const token = jwt.sign(payload, config.jwtSecret);
    return {
      user,
      token
    };
  }
}

module.exports = UserService;
