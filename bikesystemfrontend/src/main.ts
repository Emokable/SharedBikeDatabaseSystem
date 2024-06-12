import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import axios from "./api/request.js"; //引入request.js
import { createPinia } from "pinia";

const app = createApp(App);
const store = createPinia();

app.use(router);
app.use(store);

app.provide("$axios", axios);
app.mount("#app");
// 全局挂载 axios
app.config.globalProperties.$axios = axios; //配置axios的全局引用
