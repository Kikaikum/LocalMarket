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

  async findByUsernamePublic(username) {
    const user = await models.User.findOne({
      where: { username }
    });
    delete user.dataValues.password;
    delete user.dataValues.token;
    return user;
  }

  async findByUsername(username) {
    const user = await models.User.findOne({
      where: { username }
    });
    
    return user;
  }
  
  async update(id, changes) {
    let rta; // Definir rta fuera de los bloques if/else

    const user = await this.findOne(id);
    if (changes.password) {
        const hash = await bcrypt.hash(changes.password, 10);
        rta = await user.update({
            ...changes,
            password: hash
        });
        delete rta.dataValues.password;
    } else {
        rta = await user.update(changes);
        delete rta.dataValues.password;
    }
    
    return rta;
}



  async delete(id) {
    const user = await this.findOne(id);
    await user.destroy();
    return { id };
  }

  async deleteUser(userId) {
    try {
      // Primero, encontrar el usuario
      const user = await this.findOne(userId);
  
      if (!user) {
        throw new Error('Usuario no encontrado');
      }
  
      // Luego, eliminar todos los productos asociados al usuario
      await user.destroy({
        include: 'products' // Esto eliminará automáticamente todos los productos asociados
      });
  
      // Finalmente, eliminar el usuario mismo
      await user.destroy();
  
      return { success: true };
    } catch (error) {
      console.error('Error al eliminar usuario:', error);
      throw error;
    }
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
      sub: user.id     
    }
    const token = jwt.sign(payload, config.jwtSecret);
    return {
      user,
      token
    };
  }
}

module.exports = UserService;
