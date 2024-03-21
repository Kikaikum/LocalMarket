import requests
import json


print("PROVA 1: GET ALL USERS")
url = 'https://kikaikum.ddns.net:3000/localmarket/v1/users/'
try:
    response = requests.get(url)
    # Verificar si la respuesta es exitosa (código 200)
    if response.status_code == 200:
        print("PROVA OK")
        #print("Contenido de la respuesta:")
        #print(response.text)
    else:
        print(f"Error: Código de estado {response.status_code}")
except requests.exceptions.RequestException as e:
    print(f"Error al realizar la solicitud: {e}")



print("PROVA 2: GET USER ID")
user_id = 13
url = f'https://kikaikum.ddns.net:3000/localmarket/v1/users/{user_id}'
try:
    response = requests.get(url)
    # Verificar si la respuesta es exitosa (código 200)
    if response.status_code == 200:
        print("PROVA OK")
        #print("Contenido de la respuesta:")
        #print(response.text)
    else:
        print(f"Error: Código de estado {response.status_code}")
except requests.exceptions.RequestException as e:
    print(f"Error al realizar la solicitud: {e}")



print("PROVA 3: CREAR NUEVO USUARIO")

# Datos del usuario a crear
new_user_data = {
    "password": "12345678",
    "username": "KIKERggtagri",
    "email": "agrggi@mail.com",
    "nombre": "Kike",
    "apellidos": "Cabrera",
    "agricultor": True
}

url = 'https://kikaikum.ddns.net:3000/localmarket/v1/users/'

try:
    response = requests.post(url, json=new_user_data)
    # Verificar si la respuesta es exitosa (código 201)
    if response.status_code == 201:
        print("PROVA OK")
        #print("Datos del usuario creado:")
        #print(response.json())  # Convertir la respuesta JSON a un diccionario
    else:
        print(f"Error: Código de estado {response.status_code}")
except requests.exceptions.RequestException as e:
    print(f"Error al realizar la solicitud: {e}")


print("PROVA 4: NO SE PUEDE CREAR USUARIO YA REGISTRADO CON MISMO EMAIL O USERNAME")

# Datos del usuario a crear
new_user_data = {
    "password": "12345678",
    "username": "KIKERggtagri",
    "email": "agrggi@mail.com",
    "nombre": "Kike",
    "apellidos": "Cabrera",
    "agricultor": True
}

url = 'https://kikaikum.ddns.net:3000/localmarket/v1/users/'

try:
    response = requests.post(url, json=new_user_data)
    # Verificar si la respuesta es exitosa (código 201)
    if response.status_code == 201:
        print("FALLO PROVA")
        #print("Datos del usuario creado:")
        #print(response.json())  # Convertir la respuesta JSON a un diccionario
    else:
        print("PROVA OK")
except requests.exceptions.RequestException as e:
    print(f"Error al realizar la solicitud: {e}")


print("PROVA 5: DATOS DEL TIPO INCORRECTO SALTA ERROR")

# Datos del usuario a crear
new_user_data = {
    "password": 12345678,
    "username": "KIKERggtagri",
    "email": "agrggi@mail.com",
    "nombre": "Kike",
    "apellidos": "Cabrera",
    "agricultor": True
}

url = 'https://kikaikum.ddns.net:3000/localmarket/v1/users/'

try:
    response = requests.post(url, json=new_user_data)
    # Verificar si la respuesta es exitosa (código 201)
    if response.status_code == 201:
        print("FALLO PROVA")
        #print("Datos del usuario creado:")
        #print(response.json())  # Convertir la respuesta JSON a un diccionario
    else:
        print("PROVA OK")
except requests.exceptions.RequestException as e:
    print(f"Error al realizar la solicitud: {e}")