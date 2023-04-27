const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/members/join',
    createProxyMiddleware({
      target: 'https://0e68-175-213-102-16.ngrok-free.app/',      
      changeOrigin: true
    })
  );
};