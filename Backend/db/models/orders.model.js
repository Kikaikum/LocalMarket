const { Model, DataTypes, Sequelize } = require('sequelize');

const ORDER_TABLE = 'orders';

const OrderSchema = {
  id: {
    allowNull: false,
    autoIncrement: true,
    primaryKey: true,
    type: DataTypes.INTEGER
  },
  clientId: {
    field: 'clientId',
    allowNull: false,
    type: DataTypes.INTEGER,
    references: {
      model: USER_TABLE,
      key: 'id'
    },
    //onUpdate: 'CASCADE',
    //onDelete: 'SET NULL' 
  },
  agricultorId : {
    field: 'agricultorId',
    allowNull: false,
    type: DataTypes.INTEGER,
    references: {
      model: USER_TABLE,
      key: 'id'
    },
    //onUpdate: 'CASCADE',
    //onDelete: 'SET NULL' 
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

class Order extends Model {
  
  static associate(models) {
    
    models.Order.belongsTo(models.User, {
      as: 'client', // alias para la relación
      foreignKey: 'clientId', // campo en la tabla de Order que referencia al id del usuario
      //onDelete: 'SET NULL'
    });

    // Relación muchos a uno: un pedido pertenece a un agricultor
    models.Order.belongsTo(models.User, {
      as: 'agricultor', // alias para la relación
      foreignKey: 'agricultorId', // campo en la tabla de Order que referencia al id del usuario
      //onDelete: 'SET NULL'
    });
  }

  static config(sequelize) {
    return {
      sequelize,
      tableName: ORDER_TABLE,
      modelName: 'Order',
      timestamps: false
    }
  }
}


module.exports = { ORDER_TABLE, OrderSchema, Order }
