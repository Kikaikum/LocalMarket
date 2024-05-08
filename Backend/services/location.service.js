const boom = require('@hapi/boom');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const { Op } = require('sequelize');

const { models } = require('./../libs/sequelize');
const { config } = require('./../config/config');
const { PI, cos } = Math;




// Función para calcular las coordenadas máximas y mínimas dentro de un diámetro de 30 km
function calcularCoordenadas(latitud, longitud) {
    // Radio de la Tierra en kilómetros
    const radioTierra = 6371; // km

    // Convertimos la latitud a radianes
    const latitudRad = latitud * (PI / 180);

    // Calculamos el factor de conversión para la longitud
    const factorConversionLongitud = cos(latitudRad);

    // Convertimos el diámetro de 30 km a grados de longitud
    const diametroEnGrados = (30 / radioTierra) * (180 / PI) / factorConversionLongitud;

    // Calculamos las coordenadas máximas y mínimas
    const latitudMaxima = latitud + (diametroEnGrados / 2);
    const latitudMinima = latitud - (diametroEnGrados / 2);
    const longitudMaxima = longitud + (diametroEnGrados / 2);
    const longitudMinima = longitud - (diametroEnGrados / 2);

    // Devolvemos un objeto con las coordenadas calculadas
    return {
        latitudMaxima,
        latitudMinima,
        longitudMaxima,
        longitudMinima
    };
}


class LocationService {
  constructor() {}

  async find(location) {

    const coordenadas = calcularCoordenadas(location.latitude,location.longitude);

    const agricultores = await models.User.findAll({
      where: {
        latitude: { 
          [Op.between]: [coordenadas.latitudMinima, coordenadas.latitudMaxima]
        },
        longitude: { 
          [Op.between]: [coordenadas.longitudMinima, coordenadas.longitudMaxima]
        }
      }
    });
    return agricultores;
  }

}

module.exports = LocationService;