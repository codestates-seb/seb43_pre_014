const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    'http://localhost:3000/',
    createProxyMiddleware({
      target: 'https://cc94-175-213-102-16.ngrok-free.app/',      
      changeOrigin: true
    })
  );
};