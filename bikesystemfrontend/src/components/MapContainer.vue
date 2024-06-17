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

      AMap.plugin(["AMap.ToolBar", "AMap.Geolocation", "AMap.MouseTool", "AMap.PolygonEditor"], function () { //异步同时加载多个插件
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

      // 当地图组件为noParkingZone时，加载Amap.PolygonEditor插件
      if (mapStatusStore.isForNoParkingZone) {
        var polygonEditor = new AMap.PolygonEditor(map);

      }

      // 创建海量点样式
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
        } else if (massMarks !== null) {
          massMarks.setMap(null);
        }
      })

      // polyline数组，用于存储所有的轨迹
      let polylines = [];
      let polylineid = [];

      // 将轨迹数据转换为需要的格式, 根据传入的记录id确定对应的轨迹
      const initializeTrackDataMask = (recordId) => {
        // 利用recordId获取在recordStore中的对应的轨迹数据
        let recordTrack = recordStore.getRecordTrackByID(recordId);
        // 清空path数组
        let path = [];
        for (let i = 0; i < recordTrack.track.length; i++) {
          path.push(new AMap.LngLat(recordTrack.track[i].lng, recordTrack.track[i].lat));
        }
        //创建 Polyline 实例
        var polyline = new AMap.Polyline({
          path: path,
          strokeWeight: 2, //线条宽度
          strokeColor: "red", //线条颜色
          lineJoin: "round", //折线拐点连接处样式
        });

        // 将轨迹添加到地图上
        if (map && polyline) {
          map.add(polyline);
        }
        polylines.push(polyline);
        // 将轨迹的id添加到polylineid数组中
        polylineid.push(recordId);
      }

      // 根据传入的记录id删除对应的轨迹
      const removeTrackDataMask = (recordId) => {
        let index = polylineid.indexOf(recordId);
        if (index !== -1) {
          map.remove(polylines[index]);
          polylines.splice(index, 1);
          polylineid.splice(index, 1);
        }
      }

      // 监听recordStore中的selectNew变量,当值不为-1时，设置轨迹
      watch(() => recordStore.selectNew, (newValue, oldValue) => {
        console.log("selectNew", newValue);
        if (newValue !== -1) {
          initializeTrackDataMask(newValue);
          // 将selectNew设置为-1
          recordStore.setSelectNew(-1);
        }
      })

      // 监听recordStore中的removeOld变量,当值不为-1时，删除轨迹
      watch(() => recordStore.removeOld, (newValue, oldValue) => {
        console.log("removeOld", newValue);
        if (newValue !== -1) {
          removeTrackDataMask(newValue);
          // 将removeOld设置为-1
          recordStore.setRemoveOld(-1);
        }
      })

    })
    .catch((e) => {
      console.log(e);
    });
});

onUnmounted(() => {
  //销毁地图，并清空地图容器
  map.destroy();
  //地图对象赋值为null
  map = null
  //清除地图容器的 DOM 元素
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
