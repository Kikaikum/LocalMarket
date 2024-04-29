const Joi = require('joi');

const id = Joi.number().integer();
const idCliente = Joi.number();
const idAgricultor = Joi.number();

const itemSchema = Joi.object({
  itemId: Joi.number().integer().required(),
  cantidad: Joi.number().integer().min(0).required() 
});

const lista = Joi.array().items(itemSchema);


const createProductSchema = Joi.object({
  idCliente: idCliente.required(),
  idAgricultor: idAgricultor.required(),
  lista: lista.required()
  
}).options({ stripUnknown: true });
  
const updateProductSchema = Joi.object({  
}).options({ stripUnknown: true });


const getProductSchema = Joi.object({
  id: id.required(),
}).options({ stripUnknown: true });

module.exports = { createProductSchema, updateProductSchema, getProductSchema }
