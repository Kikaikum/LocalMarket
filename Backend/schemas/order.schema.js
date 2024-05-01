const Joi = require('joi');

const id = Joi.number().integer();
const clientId = Joi.number();
const agricultorId = Joi.number();

const itemSchema = Joi.object({
  itemId: Joi.number().integer().required(),
  cantidad: Joi.number().integer().min(0).required() 
});

const pedido = Joi.array().items(itemSchema);


const createOrderSchema = Joi.object({
  clientId: clientId.required(),
  agricultorId: agricultorId.required(),
  pedido: pedido.required()
  
}).options({ stripUnknown: true });
  
const updateOrderSchema = Joi.object({  
}).options({ stripUnknown: true });


const getOrderSchema = Joi.object({
  id: id.required(),
}).options({ stripUnknown: true });

module.exports = { createOrderSchema, updateOrderSchema, getOrderSchema }
