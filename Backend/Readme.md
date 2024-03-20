# API de Usuarios

API para administrar usuarios.

## Instalación

1. Clona este repositorio.
2. Instala las dependencias con `npm install`.
3. Inicia el servidor con `npm start`.

## Rutas

### Obtener todos los usuarios

GET /users

Obtiene una lista de todos los usuarios registrados.

### Crear un nuevo usuario

POST /users

Crea un nuevo usuario. Se requiere un cuerpo de solicitud JSON con los detalles del usuario.

### Obtener un usuario por ID

GET /users/{id}

Obtiene un usuario específico según su ID.

### Actualizar un usuario por ID

PATCH /users/{id}

Actualiza un usuario específico según su ID. Se requiere un cuerpo de solicitud JSON con los detalles actualizados del usuario.

### Eliminar un usuario por ID

DELETE /users/{id}

Elimina un usuario específico según su ID.

### Obtener un usuario por nombre de usuario

GET /users/username/{username}

Obtiene un usuario específico según su nombre de usuario.

### Iniciar sesión de usuario

POST /login


Inicia sesión de usuario. Se requiere un cuerpo de solicitud JSON con el nombre de usuario y la contraseña. Devuelve un token JWT si el inicio de sesión es exitoso.

## Esquema del Usuario

```json
{
  "id": "integer",
  "username": "string",
  "email": "string",
  "nombre": "string",
  "apellidos": "string",
  "agricultor": "boolean",
  "createdAt": "string"
}

Ejemplo de solicitud de inicio de sesión
{
  "username": "nombredeusuario",
  "password": "contraseña"
}
Ejemplo de respuesta de inicio de sesión exitosa
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjE3LCJyb2xlIjoiS0lLRVJ0YWdyaSIsImlhdCI6MTcxMDk1ODMyMH0.ulEwsC-l9oXHMmDVJgfjIDX7RiUcax31tJGm1xm5mzc"
}
Dependencias
Express
Passport
