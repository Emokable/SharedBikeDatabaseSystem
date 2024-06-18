<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-11 21:16:33
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 23:07:49
-->
<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
import navBar from './components/NavBar.vue'
import { mount } from '@vue/test-utils';


</script>

<template>
  <div class="total-layout">
    <navBar />
    <div class = "container"></div>
    <RouterView>
    </RouterView>
  </div>

</template>

<style scoped>

@import "./global.scss";

  #app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

</style>