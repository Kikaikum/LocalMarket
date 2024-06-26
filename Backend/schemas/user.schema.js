const Joi = require('joi');

const id = Joi.number().integer();
const email = Joi.string().email();
const password = Joi.string().min(8);
const agricultor = Joi.boolean();
const username = Joi.string().min(4)
const nombre = Joi.string().min(2)
const apellidos = Joi.string().min(2)
const latitude = Joi.number()
const longitude = Joi.number()

const createUserSchema = Joi.object({
  email: email.required(),
  username: username.required(),
  nombre: nombre.required(),
  apellidos: apellidos.required(),
  password: password.required(),
  agricultor: agricultor,
  longitude: longitude,
  latitude: latitude
}).options({ stripUnknown: true });

const updateUserSchema = Joi.object({
  email: email,
  password :password,
  username :username,
  nombre: nombre,
  apellidos: apellidos,
  
}).options({ stripUnknown: true });


const getUserSchema = Joi.object({
  id: id.required(),
});

module.exports = { createUserSchema, updateUserSchema, getUserSchema }
