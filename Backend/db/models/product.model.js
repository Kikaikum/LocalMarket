const { Model, DataTypes, Sequelize } = require('sequelize');
const { USER_TABLE } = require('./user.model');

const PRODUCT_TABLE = 'products';

const ProductSchema = {
  id: {
    allowNull: false,
    autoIncrement: true,
    primaryKey: true,
    type: DataTypes.INTEGER
  },
  nombre: {
    allowNull: false,
    type: DataTypes.STRING
  },  
  categoriaId: {
    allowNull: false,
    type: DataTypes.INTEGER
  },  
  precio: {
    allowNull: false,
    type: DataTypes.DOUBLE
  },  
  unidadMedida: {
    allowNull: false,
    type: DataTypes.STRING
  },  
  descripcion: {
    allowNull: false,
    type: DataTypes.STRING
  },  
  idAgricultor: {
    allowNull: false,
    type: DataTypes.INTEGER,
    references: {
      model: USER_TABLE,
      key: 'id'
    },
    onUpdate: 'CASCADE',
    onDelete: 'SET NULL'
  },  
  stock: {
    allowNull: false,
    type: DataTypes.DOUBLE
  },  
  createdAt: {
    allowNull: false,
    type: DataTypes.DATE,
    field: 'create_at',
    defaultValue: Sequelize.NOW
  }
}

class Product extends Model {
  
    static associate(models) {

      models.Product.belongsTo(models.User, {
        as: 'user' 
      });
    }

  static config(sequelize) {
    return {
      sequelize,
      tableName: PRODUCT_TABLE,
      modelName: 'Product',
      timestamps: false
    }
  }
}


module.exports = { PRODUCT_TABLE, ProductSchema, Product }
