<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-05 02:33:45
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-06 00:52:46
-->
<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <AdminLogin msg="Welcome to Your Vue.js App"/>
  <MapContainer msg="Bikes Map"/>
</template>

<script>
import AdminLogin from './components/AdminLogin.vue'
import MapContainer from './components/mapTools/MapContainer.vue'

export default {
  name: 'App',
  components: {
    AdminLogin,
    MapContainer
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