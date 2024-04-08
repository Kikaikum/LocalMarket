const Joi = require('joi');

const id = Joi.number().integer();
const nombre = Joi.string().email();
const categoriaId = Joi.number();
const precio = Joi.number();
const unidadMedida = Joi.string()
const descripcion = Joi.string().min(2)
const idAgricultor = Joi.number()
const stock = Joi.number()

const createProductSchema = Joi.object({
  
}).options({ stripUnknown: true });;

const updateProductSchema = Joi.object({
  
  
});


const getProductSchema = Joi.object({
  id: id.required(),
});

module.exports = { createProductSchema, updateProductSchema, getProductSchema }
