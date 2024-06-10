<script setup>
import { reactive, toRefs, ref } from 'vue'
import { onMounted, onUnmounted } from "vue"
import axios from 'axios'
import AMapLoader from "@amap/amap-jsapi-loader"

let map = null;

const bikeID = ref('');

const getLocation = () => {
  // Your code here
  axios
        .post('http://localhost:8088/location', {
          bikeID: parseInt(bikeID.value)
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            // 修改地图中心点为返回的经纬度
            map.setCenter([successResponse.data.longitude, successResponse.data.latitude])
            // 弹窗提示
            window.alert('查找成功')
          } else {
            window.alert('查找失败：' + successResponse.data.message)
          }
        })
        .catch(failResponse => {
          console.log(failResponse)
          window.alert('查找失败：无法连接到服务器')
        })
};

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

        // geolocation.getCurrentPosition(function (status, result) {
        //   if (status == 'complete') {
        //     onComplete(result)
        //   } else {
        //     onError(result)
        //   }
        // });

        // function onComplete(data) {
        //   // data是具体的定位信息, 弹窗提示
        //   alert('定位成功') 
        // }

        // function onError(data) {
        //   // 定位出错
        //   alert('定位失败')
        // }

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
    <input type="text" v-model="bikeID" placeholder="Enter BikeID" />
    <button @click="getLocation">Get Location</button>
    <div id="container"></div>
  </div>
</template>

<style scoped>
#container {
  width: 60%;
  height: 800px;
}
</style>
