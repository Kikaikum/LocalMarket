const boom = require('@hapi/boom');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

const { models } = require('./../libs/sequelize');
const { config } = require('./../config/config');

class ProductService {
  constructor() {}

  async create(data) {    
    const newProduct = await models.Product.create(data);    
    return newProduct;
  }

  async find() {
    const products = await models.Product.findAll();    
    return products;
  }

  async findOne(id) {
    const product = await models.Product.findByPk(id);
    if (!product) {
      throw boom.notFound('product not found');
    }
    return product;
  }

  async findByProductname(productname) {
    const product = await models.Product.findOne({
      where: { productname }
    });
    return product;
  }

  async update(id, changes) {
    let rta; // Definir rta fuera de los bloques if/else
    const product = await this.findOne(id);
    rta = await product.update(changes);
    return rta;
}



  async delete(id) {
    const product = await this.findOne(id);
    await product.destroy();
    return { id };
  }

  async getProduct(productname, password) {
    const product = await this.findByProductname(productname);
    if (!product) {
      throw boom.unauthorized();
    }
    const isMatch = await bcrypt.compare(password, product.password);
    if (!isMatch) {
      throw boom.unauthorized();;
    }
    delete product.dataValues.password;
    return product;
  }

  signToken(product) {
    const payload = {
      sub: product.id,
      role: product.productname
    }
    const token = jwt.sign(payload, config.jwtSecret);
    return {
      product,
      token
    };
  }
}

module.exports = ProductService;
