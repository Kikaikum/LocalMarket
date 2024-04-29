const { Model, DataTypes, Sequelize } = require('sequelize');

const USER_TABLE = 'users';

const UserSchema = {
  id: {
    allowNull: false,
    autoIncrement: true,
    primaryKey: true,
    type: DataTypes.INTEGER
  },
  clientId: {
    allowNull: false,
    type: DataTypes.INTEGER,
    unique: true,
  },
  agricultorId : {
    allowNull: false,
    type: DataTypes.INTEGER,
    unique: true,
  },
  pedido: {
    allowNull: false,
    type: DataTypes.ARRAY(DataTypes.JSON)
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
    
    models.Product.belongsTo(models.User, {
      as: 'user',
      foreignKey: 'id'
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
