const Joi = require('joi');

const id = Joi.number().integer();
const nombre = Joi.string();
const categoriaId = Joi.number();
const precio = Joi.number();
const unidadMedida = Joi.string()
const descripcion = Joi.string().min(2)
const idAgricultor = Joi.number()
const stock = Joi.number()

const createProductSchema = Joi.object({
  nombre: nombre.required(),
  categoriaId: categoriaId.required(),
  precio: precio.required(),
  unidadMedida: unidadMedida.required(),
  descripcion: descripcion.required(),
  idAgricultor: idAgricultor.required(),
  stock: stock.required()  
}).options({ stripUnknown: true });
  
const updateProductSchema = Joi.object({  
}).options({ stripUnknown: true });


const getProductSchema = Joi.object({
  id: id.required(),
}).options({ stripUnknown: true });

module.exports = { createProductSchema, updateProductSchema, getProductSchema }
