const boom = require('@hapi/boom');
const { models } = require('./../libs/sequelize');


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

  async findByAgricultor(agricultor) {
    const products = await models.Product.findAll({
      where: { idAgricultor: agricultor }
    });
    
    return products;
  }


  async update(id, changes, authenticatedUserId) {
    const product = await this.findOne(id);    
    if (authenticatedUserId === product.idAgricultor) {
      const updatedProduct = await product.update(changes);
      return updatedProduct;
    } else {
      throw new Error("No tienes permiso para actualizar este producto.");
    }
  }

  async delete(id,authenticatedUserId) {
    const product = await this.findOne(id);
    if (authenticatedUserId === product.idAgricultor){
      await product.destroy();
      return { id };
    } else {
      throw new Error("No tienes permiso para eliminar este producto.");
    }
  }

  
}

module.exports = ProductService;
