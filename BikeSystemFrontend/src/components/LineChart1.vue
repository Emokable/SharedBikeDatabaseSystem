
<template>
  <div class="lineChart">
    <div id="lineChart" style="width: 1200px; height: 500px;"></div>
  </div>
</template>

<script setup lang="ts">

import { ref, onMounted, onUnmounted } from 'vue'
import { http } from '../utils/http'
import { useUserStore } from '../stores/user';
import * as echarts from 'echarts'

const userStore = useUserStore()
let echart = echarts;
let myChart;
//配置项函数
const chartOption = (data) => myChart.setOption({
  //配置折线图
    title: {
        text: '每日平均骑行时长'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['平均骑行时长']
    },
    xAxis: {
        type: 'category',
        boundaryGap: true,
        data: data.map((item) => item.date)
    },
    yAxis: {
        type: 'value',
    },
    series: [{
        name: '平均骑行时长',
        type: 'line',
        smooth: true,
        data: data.map((item) => item.avg_ride_time)
    }]
});

//在onMounted生命周期中初始化myChart
onMounted(() => {
    //初始化myChart
    myChart = echart.init(document.getElementById('lineChart'));
    http.get('/rideRecords/averageRideTimePerDay', userStore.token).then((res) => {
    //将数据传入配置项
    chartOption(res.data.data);
  });
});

// 在组件销毁时销毁myChart
onUnmounted(() => {
    myChart.dispose();
});
</script>