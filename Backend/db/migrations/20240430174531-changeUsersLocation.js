'use strict';

const { UserSchema, USER_TABLE } = require('./../models/user.model');

module.exports = {
  up: async (queryInterface, Sequelize) => {
    // Añadir columnas latitude y longitude
    await queryInterface.addColumn(USER_TABLE, 'latitude', {
      type: Sequelize.DOUBLE,
      allowNull: true,
      defaultValue: null
    });

    await queryInterface.addColumn(USER_TABLE, 'longitude', {
      type: Sequelize.DOUBLE,
      allowNull: true,
      defaultValue: null
    });

    // Eliminar columna localizacion
    await queryInterface.removeColumn(USER_TABLE, 'localizacion');
  },

  down: async (queryInterface, Sequelize) => {
    // Añadir columna localizacion
    await queryInterface.addColumn(USER_TABLE, 'localizacion', {
      type: Sequelize.STRING,
      allowNull: true,
      defaultValue: false
    });

    // Eliminar columnas latitude y longitude
    await queryInterface.removeColumn('users', 'latitude');
    await queryInterface.removeColumn('users', 'longitude');
  }
};