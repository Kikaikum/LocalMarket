const https = require('https');
const fs = require('fs');
const express = require('express');
const cors = require('cors');
const routerApi = require('./routes');

const { logErrors, errorHandler, boomErrorHandler, ormErrorHandler } = require('./middlewares/error.handler');

const app = express();
const port = process.env.PORT || 443;

app.use(express.json());

const whitelist = ['http://localhost:8080','https://kikaikum.ddns.net'];
const options = {
  origin: (origin, callback) => {
    if (whitelist.includes(origin) || !origin) {
      callback(null, true);
    } else {
      callback(new Error('no permitido'));
    }
  }
}
app.use(cors(options));

app.get('/', (req, res) => {
  res.send('Hola mi server en express');
});


routerApi(app);

app.use(logErrors);
app.use(ormErrorHandler);
app.use(boomErrorHandler);
app.use(errorHandler);

// Configurar el servidor HTTPS con el certificado SSL/TLS
const privateKeyPath = '/etc/letsencrypt/live/kikaikum.ddns.net/privkey.pem';
const certificatePath = '/etc/letsencrypt/live/kikaikum.ddns.net/fullchain.pem';

const privateKey = fs.readFileSync(privateKeyPath, 'utf8');
const certificate = fs.readFileSync(certificatePath, 'utf8');

const credentials = { key: privateKey, cert: certificate };

const httpsServer = https.createServer(credentials, app);

httpsServer.listen(port, () => {
  console.log('Mi server está escuchando en el puerto ' + port);
});
