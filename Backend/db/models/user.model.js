const { Model, DataTypes, Sequelize } = require('sequelize');

const USER_TABLE = 'users';

const UserSchema = {
  id: {
    allowNull: false,
    autoIncrement: true,
    primaryKey: true,
    type: DataTypes.INTEGER
  },
  username: {
    allowNull: false,
    type: DataTypes.STRING,
    unique: true,
  },
  email: {
    allowNull: false,
    type: DataTypes.STRING,
    unique: true,
  },
  password: {
    allowNull: false,
    type: DataTypes.STRING
  },
  nombre: {
    allowNull: false,
    type: DataTypes.STRING
  },
  apellidos: {
    allowNull: false,
    type: DataTypes.STRING
  },
  agricultor: {
    allowNull: false,
    type: DataTypes.BOOLEAN,
    defaultValue: false
  },
  latitude: {
    allowNull: true,
    type: DataTypes.DOUBLE,
    defaultValue: null 
  },
  longitude: {
    allowNull: true,
    type: DataTypes.DOUBLE,
    defaultValue: null 
  },
  createdAt: {
    allowNull: false,
    type: DataTypes.DATE,
    field: 'create_at',
    defaultValue: Sequelize.NOW
  }
}

class User extends Model {
  
  static associate(models) {
    
    models.User.hasMany(models.Product, {
      as: 'products',
      foreignKey: 'idAgricultor',
      onDelete: 'CASCADE'      
    });

     models.User.hasMany(models.Order, {
      as: 'clientOrders', 
      foreignKey: 'clientId' 
    });

    models.User.hasMany(models.Order, {
      as: 'agricultorOrders', 
      foreignKey: 'agricultorId' 
    });
  }

  static config(sequelize) {
    return {
      sequelize,
      tableName: USER_TABLE,
      modelName: 'User',
      timestamps: false
    }
  }
}


module.exports = { USER_TABLE, UserSchema, User }
