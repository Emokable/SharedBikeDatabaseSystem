<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-12 19:07:14
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-15 17:53:12
-->
<script setup>
import { reactive, toRefs, ref } from 'vue'
import { onMounted, onUnmounted } from "vue"
import axios from 'axios'
import AMapLoader from "@amap/amap-jsapi-loader"
import { useMapStore } from '../stores/map'

let map = null;

const mapStore = useMapStore()

// 初始化 data
const data = ref([]);

// 将空闲单车的数据转换为需要的格式
const initializeBikeData = () => {
  data.value = mapStore.getFreeBikes.map(bike => ({
    lnglat: [bike.location.latitude, bike.location.longitude]
  }));
}



onMounted(() => {
  window._AMapSecurityConfig = {
    securityJsCode: "662998a630a974058bdb30d05ffd7c6a",
  };
  AMapLoader.load({
    key: "c188a059caf8eb3830b2f195a809c36a", // 申请好的Web端开发者Key，首次调用 load 时必填
    version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
    plugins: ["AMap.Scale"], //需要使用的的插件列表，如比例尺'AMap.Scale'，支持添加多个如：['...','...']

  })
    .then((AMap) => {

      var style = {
        url: "//vdata.amap.com/icons/b18/1/2.png", //图标地址
        size: new AMap.Size(11, 11), //图标大小
        anchor: new AMap.Pixel(5, 5), //图标显示位置偏移量，基准点为图标左上角
      };

      let massMarks = new AMap.MassMarks(data, {
        zIndex: 5, //海量点图层叠加的顺序
        zooms: [3, 19], //在指定地图缩放级别范围内展示海量点图层
        style: style, //设置样式对象
      });

      map = new AMap.Map("container", {
        // 设置地图容器id
        mapStyle: "amap://styles/black", //设置地图的显示样式
        viewMode: "3D", // 是否为3D地图模式
        zoom: 11, // 初始化地图级别
      });

      AMap.plugin(["AMap.ToolBar", "AMap.Geolocation"], function () { //异步同时加载多个插件
        var toolbar = new AMap.ToolBar(); //创建工具条插件实例
        map.addControl(toolbar); //添加工具条插件到页面

        var geolocation = new AMap.Geolocation({
          enableHighAccuracy: true, // 是否使用高精度定位，默认：true
          timeout: 10000, // 设置定位超时时间，默认：无穷大
          offset: [10, 20],  // 定位按钮的停靠位置的偏移量
          zoomToAccuracy: true,  //  定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
          position: 'RB' //  定位按钮的排放位置,  RB表示右下
        })



      });
    })
    .catch((e) => {
      console.log(e);
    });
});

onUnmounted(() => {
  map?.destroy();
});
</script>

<template>
  <div>
    <div id="container"></div>
  </div>
</template>

<style scoped>
#container {
  width: 95%;
  height: 800px;
  border: 10px solid #ccc;
}
</style>
