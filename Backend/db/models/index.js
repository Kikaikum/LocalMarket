const { User, UserSchema } = require('./user.model');
const { Product, ProductSchema } = require('./product.model');

function setupModels(sequelize) {
  User.init(UserSchema, User.config(sequelize));
  Product.init(ProductSchema, Product.config(sequelize));
  

  Product.associate(sequelize.models);
  User.associate(sequelize.models);
}

module.exports = setupModels;
