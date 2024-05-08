# API de LocalMarket

API para administrar usuarios, productos y ordenes.

## Instalación

1. Clona este repositorio.
2. Instala las dependencias con `npm install`.
3. Inicia el servidor con `npm start`.

## Rutas
url : https://kikaikum.ddns.net:3000/localmarket/v1/
### Usuarios

- **Obtener todos los usuarios**
  - **Método:** GET
  - **Ruta:** /users
  - **Descripción:** Obtiene una lista de todos los usuarios registrados.

- **Crear un nuevo usuario**
  - **Método:** POST
  - **Ruta:** /users
  - **Descripción:** Crea un nuevo usuario. Se requiere un cuerpo de solicitud JSON con los detalles del usuario.

- **Obtener un usuario por ID**
  - **Método:** GET
  - **Ruta:** /users/{id}
  - **Descripción:** Obtiene un usuario específico según su ID.

- **Actualizar un usuario por ID**
  - **Método:** PATCH
  - **Ruta:** /users/{id}
  - **Descripción:** Actualiza un usuario específico según su ID. Se requiere un cuerpo de solicitud JSON con los detalles actualizados del usuario.

- **Eliminar un usuario por ID**
  - **Método:** DELETE
  - **Ruta:** /users/{id}
  - **Descripción:** Elimina un usuario específico según su ID.

- **Obtener un usuario por nombre de usuario**
  - **Método:** GET
  - **Ruta:** /users/username/{username}
  - **Descripción:** Obtiene un usuario específico según su nombre de usuario.

- **Iniciar sesión de usuario**
  - **Método:** POST
  - **Ruta:** /login
  - **Descripción:** Inicia sesión de usuario. Se requiere un cuerpo de solicitud JSON con el nombre de usuario y la contraseña. Devuelve un token JWT si el inicio de sesión es exitoso.

### Productos

- **Obtener todos los productos**
  - **Método:** GET
  - **Ruta:** /products
  - **Descripción:** Obtiene una lista de todos los productos.

- **Crear un nuevo producto**
  - **Método:** POST
  - **Ruta:** /products
  - **Descripción:** Crea un nuevo producto. Se requiere un cuerpo de solicitud JSON con los detalles del producto.

- **Obtener un producto por ID**
  - **Método:** GET
  - **Ruta:** /products/{id}
  - **Descripción:** Obtiene un producto específico según su ID.

- **Actualizar un producto por ID**
  - **Método:** PATCH
  - **Ruta:** /products/{id}
  - **Descripción:** Actualiza un producto específico según su ID. Se requiere un cuerpo de solicitud JSON con los detalles actualizados del producto.

- **Eliminar un producto por ID**
  - **Método:** DELETE
  - **Ruta:** /products/{id}
  - **Descripción:** Elimina un producto específico según su ID.

- **Obtener todos los productos de un agricultor**
  - **Método:** GET
  - **Ruta:** /products/agricultor/{id}
  - **Descripción:** Obtiene todos los productos de un agricultor específico según su ID.

### Órdenes

- **Crear una nueva orden**
  - **Método:** POST
  - **Ruta:** /orders
  - **Descripción:** Crea una nueva orden. Se requiere un cuerpo de solicitud JSON con los detalles de la orden.

- **Obtener todas las órdenes**
  - **Método:** GET
  - **Ruta:** /orders
  - **Descripción:** Obtiene un listado de todas las órdenes.

- **Obtener una orden por ID**
  - **Método:** GET
  - **Ruta:** /orders/{id}
  - **Descripción:** Obtiene una orden específica según su ID.

- **Obtener las órdenes de un agricultor**
  - **Método:** GET
  - **Ruta:** /orders/agricultor/:idAgricultor
  - **Descripción:** Obtiene las órdenes de un agricultor específico.

- **Obtener las órdenes de un cliente**
  - **Método:** GET
  - **Ruta:** /orders/client/:idClient
  - **Descripción:** Obtiene las órdenes de un cliente específico.

- **Actualizar una orden por ID**
  - **Método:** PATCH
  - **Ruta:** /orders/{id}
  - **Descripción:** Actualiza el estado de la orden según su ID.

- **Eliminar una orden por ID**
  - **Método:** DELETE
  - **Ruta:** /orders/{id}
  - **Descripción:** Elimina una orden específica según su ID.

### Localización

- **Obtener los agricultores en un radio de 30km**
  - **Método:** GET
  - **Ruta:** /location
  - **Descripción:** Obtiene un listado de todos los agricultores en un radio de 30km.


## Esquema del Usuario

```json
{
  "id": "integer",
  "username": "string",
  "email": "string",
  "nombre": "string",
  "apellidos": "string",
  "agricultor": "boolean",
  "createdAt": "string",
  "latitude": "double",
  "longitude": "double"
}
```
## Ejemplo de solicitud de inicio de sesión
```json
{
  "username": "nombredeusuario",
  "password": "contraseña"
}
```
## Ejemplo de respuesta de inicio de sesión exitosa
```json
{
	"user": {
		"id": 17,
		"username": "test",
		"email": "test@mail.com",
		"nombre": "test",
		"apellidos": "test",
		"agricultor": true,
		"createdAt": "2024-03-19T21:49:03.697Z"
	},
	"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjE3LCJyb2xlIjoiS0lLRVJ0YWdyaSIsImlhdCI6MTcxMDk1ODMyMH0.ulEwsC-l9oXHMmDVJgfjIDX7RiUcax31tJGm1xm5mzc"
}
```
## Dependencias
Express
Passport
Joi
JWT
Sequelize
PostgreSQL
Docker