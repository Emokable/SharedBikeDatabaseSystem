<template>
  <div class="mapPanel-layout">
    <el-container>
      <el-header class="head">
        实时地图信息查看
      </el-header>
      <el-container>
        <el-aside class="bordered" width="200px">
          显示信息选择
          <el-button @click="MassMarksVisible" v-if="!Massset" >显示空闲单车</el-button>
          <el-button @click="MassMarksUnvisible" v-else>隐藏空闲单车</el-button>


        </el-aside>

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
import { ref } from 'vue';
import MapContainer from '../components/MapContainer.vue';
import { useMapStatusStore } from '../stores/mapStatus';

const mapStatusStore = useMapStatusStore();
const Massset = ref(false);
const MassMarksVisible = () => {
  mapStatusStore.setMassMarksLoadedStatus(true);
  console.log(mapStatusStore.isMassMarksLoaded);
  Massset.value = true;
};

const MassMarksUnvisible = () => {
  mapStatusStore.setMassMarksLoadedStatus(false);
  console.log(mapStatusStore.isMassMarksLoaded);
  Massset.value = false;
};
</script>

<style scoped>

.head {
  border: 1px solid #000; 
  background-color: #5b91c7;
  justify-content: center;
  text-align: center;
  font-size: 20px;
  display: flex; /* Ensure the header is a flex container */
  align-items: center; /* Center the button vertically */
}
.bordered {
  border: 1px solid #000; 
}
</style>