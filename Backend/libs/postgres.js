const { Client } = require('pg');

async function getConnection() {
  const client = new Client({
    host: '0000',
    port: "0000",
    user: '0000',
    password: '0000',
    database: '0000'
  });
  await client.connect();
  return client;
}

module.exports = getConnection;
