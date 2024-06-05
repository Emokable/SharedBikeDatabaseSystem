<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-05 02:33:45
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-05 13:56:29
-->
<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <AdminLogin msg="Welcome to Your Vue.js App"/>
</template>

<script>
import AdminLogin from './components/AdminLogin.vue'

export default {
  name: 'App',
  components: {
    AdminLogin
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>