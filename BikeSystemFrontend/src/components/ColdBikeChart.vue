<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-18 05:57:05
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 06:57:26
-->

<template>
    <div class="coldBikeChart">
      <div id="coldBikeChart" style="width: 1200px; height: 500px;"></div>
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
          text: '最频繁单车统计'
      },
      tooltip: {
          trigger: 'axis'
      },
      legend: {
          data: ['单车使用次数']
      },
      xAxis: {
          type: 'category',
          boundaryGap: true,
          data: data.map((item) => item.bikeid)
      },
      yAxis: {
          type: 'value',
      },
      series: [{
          name: '单车使用次数',
          type: 'bar',
          smooth: true,
          data: data.map((item) => item.ride_count)
      }]
  });
  
  //在onMounted生命周期中初始化myChart
  onMounted(() => {
      //初始化myChart
      myChart = echart.init(document.getElementById('coldBikeChart'));
      http.get('/rideRecords/leastFrequentBikes', userStore.token).then((res) => {
      //将数据传入配置项
      chartOption(res.data.data);
    });
  });
  
  // 在组件销毁时销毁myChart
  onUnmounted(() => {
      myChart.dispose();
  });
  </script>