const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
      '/members',
      createProxyMiddleware({
        target: 'https://6a4c-175-213-102-16.ngrok-free.app',
        changeOrigin: true,
      })
    );
  };