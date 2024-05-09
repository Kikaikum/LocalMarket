const { Op } = require('sequelize');

const { models } = require('./../libs/sequelize');
const { PI, cos } = Math;




// Función para calcular las coordenadas máximas y mínimas dentro de un diámetro de 30 km
function calcularCoordenadas(latitud, longitud) {
  // Radio de la Tierra en kilómetros
  const radioTierra = 6371; // km

  
  latitud = Number(latitud);
  longitud = Number(longitud);

  // Convertimos la latitud a radianes
  const latitudRad = latitud * (PI / 180);

  // El diámetro en grados de latitud
  const diametroLatitudEnGrados = (30 / radioTierra) * (180 / PI);

  // Longitud por grado basado en la latitud
  const longitudPorGrado = radioTierra * cos(latitudRad);
  const diametroLongitudEnGrados = (30 / longitudPorGrado) * (180 / PI);

  // Calculamos las coordenadas máximas y mínimas
  const latitudMaxima = latitud + (diametroLatitudEnGrados / 2);
  const latitudMinima = latitud - (diametroLatitudEnGrados / 2);
  const longitudMaxima = longitud + (diametroLongitudEnGrados / 2);
  const longitudMinima = longitud - (diametroLongitudEnGrados / 2);

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
    console.log("latitud maxima = "+coordenadas.latitudMaxima)
    console.log("latitud minima = "+coordenadas.latitudMinima)
    console.log("longitud maxima = "+coordenadas.longitudMaxima)
    console.log("longitud minima = "+coordenadas.longitudMinima)
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
    agricultores.forEach((usuario) => {
      delete usuario.dataValues.password;
    });
    return agricultores;
  }

}

module.exports = LocationService;
