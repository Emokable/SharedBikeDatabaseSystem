<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-12 01:43:24
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 04:58:50
-->
<template>
  <div class="mapPanel-layout">
    <el-container>
      <el-header class="head">
        实时地图信息查看
      </el-header>
      <el-container>
        <el-aside class="bordered" width="200px">
          显示信息选择
          <el-button @click="MassMarksVisible" v-if="!Massset">显示空闲单车</el-button>
          <el-button @click="MassMarksUnvisible" v-else>隐藏空闲单车</el-button>
          <el-button @click="showLineChartModal = true">显示每日单次骑行时间</el-button>
          <el-button @click="statusStore.setGetingSchedule(true)">查询调度方案</el-button>
        </el-aside>
        <el-container>
          <el-main class="bordered">
            <MapContainer />
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>

  <el-dialog v-model="showLineChartModal" title="折线统计图" width="1200px">
    <LineChart1> </LineChart1>
  </el-dialog>

  <div class="time-select">
    <SchedulingPlan v-if="statusStore.isGetingSchedule"></SchedulingPlan>
  </div>

</template>

<script setup lang="ts">
import { ref } from 'vue';
import MapContainer from '../components/MapContainer.vue';
import { useMapStatusStore } from '../stores/mapStatus';
import { useStatusStore } from '../stores/operationStatus';

const mapStatusStore = useMapStatusStore();
const statusStore = useStatusStore();
const Massset = ref(false);
const showLineChartModal = ref(false);
const showSchedulingPlan = ref(false);
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
  display: flex;
  /* Ensure the header is a flex container */
  align-items: center;
  /* Center the button vertically */
}

.bordered {
  border: 1px solid #000;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
</style>