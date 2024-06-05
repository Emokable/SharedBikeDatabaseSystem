const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
})

module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // 设置为本地的8080端口
        changeOrigin: true,
        pathRewrite: { '^/api': '' },
      },
    },
  },
};

