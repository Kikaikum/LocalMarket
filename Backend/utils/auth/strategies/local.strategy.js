const { Strategy } = require('passport-local');

const UserService = require('./../../../services/user.service');
const service = new UserService();

const LocalStrategy = new Strategy({
    usernameField: 'username',
    passwordField: 'password'
  },
  async (username, password, done) => {
    try {
      const user = await service.getUser(username, password);
      done(null, user);
    } catch (error) {
      done(error, false);
    }
  }
);

module.exports = LocalStrategy;
