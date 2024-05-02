'use strict';

const { OrderSchema, ORDER_TABLE } = require('./../models/order.model');

module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.addColumn(ORDER_TABLE, 'estado', {
      type: Sequelize.STRING,
      allowNull: false,
      defaultValue: null
    });



  },

  down: async (queryInterface) => {

    await queryInterface.removeColumn('orders', 'estado');

  }
};