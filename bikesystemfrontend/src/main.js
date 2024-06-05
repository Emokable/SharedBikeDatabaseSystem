import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
// 设置反向代理，前端请求默认发送到 http://localhost:8080/api
var axios = require('axios')
axios.default.baseURL = 'http://localhost:8080'

const app = createApp(App)

// 全局注册，之后可在其他组件中通过 this.$axios 发送数据
app.config.globalProperties.$axios = axios

app.use(router)
app.mount('#app')