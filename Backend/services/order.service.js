const boom = require('@hapi/boom');
const { models } = require('./../libs/sequelize');

class OrderService {
  constructor() {}

  async create(data) {  
    console.log(data);  
    const newOrder = await models.Order.create(data);    
    return newOrder;
  }

  async find() {
    const orders = await models.Order.findAll();    
    return orders;
  }

  async findOne(id) {
    const order = await models.Order.findByPk(id);
    if (!order) {
      throw boom.notFound('order not found');
    }
    return order;
  }

  async findByAgricultor(agricultor) {
    const orders = await models.Order.findAll({
      where: { agricultorId: agricultor }
    });
    
    return orders;
  }
  async findByClient(client) {
    const orders = await models.Order.findAll({
      where: { clientId: client }
    });
    
    return orders;
  }

  async update(id, changes, authenticatedUserId) {
    const order = await this.findOne(id);    
    if (authenticatedUserId === order.agricultorId) {
      const updatedOrder = await order.update(changes);
      return updatedOrder;
    } else {
      throw new Error("No tienes permiso para actualizar esta orden.");
    }
  }

  async delete(id,authenticatedUserId) {
    const order = await this.findOne(id);
    if (authenticatedUserId === order.idAgricultor){
      await order.destroy();
      return { id };
    } else {
      throw new Error("No tienes permiso para eliminar este ordero.");
    }
  }

  
}

module.exports = OrderService;
