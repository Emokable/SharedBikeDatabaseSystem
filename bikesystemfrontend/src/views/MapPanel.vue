<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-12 01:43:24
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 06:51:34
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
          <!-- <el-button @click="mapStatusStore.setHotAreaLoadedStatus(true)" v-if="!mapStatusStore.isHotAreaLoaded">显示热点地区</el-button>
          <el-button @click="mapStatusStore.setHotAreaLoadedStatus(false)" v-else>隐藏热点地区</el-button> -->
          <el-button @click="showLineChartModal = true">显示每日单次骑行时间</el-button>
          <el-button @click="statusStore.setGetingSchedule(true)">查询调度方案</el-button>
          <el-button @click="statusStore.setShowHotTimes(true)">显示热门时间</el-button>
          <el-button @click="showHotbikeModal = true">显示最频繁使用单车排行</el-button>
          <el-button @click="showColdbikeModal = true">显示最不频繁使用单车排行</el-button>
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

  <el-dialog v-model="showHotbikeModal" title="单车统计图" width="1200px">
    <HotBikeChart> </HotBikeChart>
  </el-dialog>

  <el-dialog v-model="showColdbikeModal" title="单车统计图" width="1200px">
    <ColdBikeChart> </ColdBikeChart>
  </el-dialog>



  <el-dialog v-model="statusStore.isShowHotTimes" title="时间统计图" width="1200px">
    <TimeChart> </TimeChart>
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
import { fa } from 'element-plus/lib/locale/index.js';

const mapStatusStore = useMapStatusStore();
const statusStore = useStatusStore();
const Massset = ref(false);
const showHotArea = ref(false);
const showLineChartModal = ref(false);
const showHotbikeModal = ref(false);
const showColdbikeModal = ref(false);

const MassMarksVisible = () => {
  mapStatusStore.setMassMarksLoadedStatus(true);
  console.log(mapStatusStore.isMassMarksLoaded);
  Massset.value = true;
};

const MassMarksUnvisible = () => {
  mapStatusStore.setMassMarksLoadedStatus(false);
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