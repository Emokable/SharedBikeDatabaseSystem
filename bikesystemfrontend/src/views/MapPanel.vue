<template>
  <div class="mapPanel-layout">
    <el-container>
      <el-header class="bordered">实时地图信息查看</el-header>
      <el-container>
        <el-aside class="bordered" width="200px">信息筛选</el-aside>
        <el-container>
          <el-main class="bordered">
            <MapContainer />
          </el-main>
          <el-footer class="bordered">Footer</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import MapContainer from '../components/MapContainer.vue';
</script>

<style scoped>
.bordered {
  border: 1px solid #000;
}
</style>