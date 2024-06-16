<script setup>
import { reactive, toRefs, ref, watch } from 'vue'
import { onMounted, onUnmounted } from "vue"
import AMapLoader from "@amap/amap-jsapi-loader"
import { useMapStatusStore } from '../stores/mapStatus'
import { useUserStore } from '../stores/user'
import { useRecordStore } from '../stores/records'
import { http } from '../utils/http'

let map = null;
let massMarks = null;

const mapStatusStore = useMapStatusStore()
const userStore = useUserStore()
const recordStore = useRecordStore()

// 初始化 data
let data = ref([]);

onMounted(() => {
  window._AMapSecurityConfig = {
    securityJsCode: "662998a630a974058bdb30d05ffd7c6a",
  };
  AMapLoader.load({
    key: "c188a059caf8eb3830b2f195a809c36a", // 申请好的Web端开发者Key，首次调用 load 时必填
    version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
    plugins: ["AMap.MarkerCluster", "AMap.Scale"], //需要使用的的插件列表，如比例尺'AMap.Scale'，支持添加多个如：['...','...']

  })
    .then((AMap) => {
      map = new AMap.Map("container", {
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

      var massStyle = {
        // 图标地址为Public文件夹下的img文件夹下的icon.png
        url: "/img/mass1.png", //图标地址
        size: new AMap.Size(11, 11), //图标大小
        anchor: new AMap.Pixel(5, 5), //图标显示位置偏移量，基准点为图标左上角
      };

      let massMarks = null;

      // 将空闲单车的数据转换为需要的格式
      const initializeFreeBikeDataMask = () => {
        http.get('/bikes/avabike', userStore.token).then((res) => {
          let newData = [];
          if (Array.isArray(res.data.data)) {
            for (let i = 0; i < res.data.data.length; i++) {
              let item = res.data.data[i];
              newData.push({
                lnglat: [item.locationX, item.locationY],
              });
            }
          }
          massMarks = new AMap.MassMarks(newData, {
            zIndex: 5, //海量点图层叠加的顺序
            zooms: [3, 19], //在指定地图缩放级别范围内展示海量点图层
            style: massStyle, //设置样式对象
          });
          massMarks.setMap(map);
        })
      }

      // 监听 mapStatusStore 中的 isMassMarksLoaded 变量，当其变化时，显示或隐藏海量点
      watch(() => mapStatusStore.isMassMarksLoaded, (newValue, oldValue) => {
        if (newValue) {
          initializeFreeBikeDataMask();
        } else if (massMarks !== null){
          massMarks.setMap(null);
        }
      })

      // 监听recordStore中的removeOld和selectNew变量
      



    })
    .catch((e) => {
      console.log(e);
    });
});

onUnmounted(() => {
  map?.destroy();
  massMarks = null;
  mapStatusStore.setMassMarksLoadedStatus(false);
  mapStatusStore.setTrackLoadedStatus(false);
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
