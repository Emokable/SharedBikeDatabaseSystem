<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
import navBar from './components/NavBar.vue'
</script>

<template>
  <div class="common-layout">
    <navBar />
    <RouterView>
    </RouterView>
  </div>

</template>

<style scoped>
.hello-world .logo {
  display: block;
  margin: 0 auto 2rem;
  border-radius: 50%;
  /* 让 logo 变成圆形 */
}
</style>