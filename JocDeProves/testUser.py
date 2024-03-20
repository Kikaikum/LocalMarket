import requests

url = 'https://kikaikum.ddns.net:3000/localmarket/v1/users/username/KIKE'

try:
    response = requests.get(url)
    # Verificar si la respuesta es exitosa (código 200)
    if response.status_code == 200:
        print("La solicitud fue exitosa.")
        print("Contenido de la respuesta:")
        print(response.text)
    else:
        print(f"Error: Código de estado {response.status_code}")
except requests.exceptions.RequestException as e:
    print(f"Error al realizar la solicitud: {e}")