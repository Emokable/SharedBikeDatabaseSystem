<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-16 21:23:13
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-17 04:21:47
-->
<script setup>
import { reactive, toRefs, ref, watch } from 'vue'
import { onMounted, onUnmounted } from "vue"
import AMapLoader from "@amap/amap-jsapi-loader"
import { useMapStatusStore } from '../stores/mapStatus'
import { useNoParkingZoneStore } from '../stores/noParkingZone'
import { useUserStore } from '../stores/user'
import { http } from '../utils/http'

let map = null;

const mapStatusStore = useMapStatusStore()
const noParkingZoneStore = useNoParkingZoneStore()
const userStore = useUserStore()

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

            var polygonEditor = null;
            // 监视是否处于创建状态
            watch(() => mapStatusStore.isForNoParkingZoneCreate, (newValue, oldValue) => {
                console.log(newValue);
                if (newValue) {
                    polygonEditor = new AMap.PolygonEditor(map);
                    noParkingZoneStore.newNoparkingZoneName = '';
                    polygonEditor.open();
                } else {
                    const path = polygonEditor.getTarget()._opts.path;
                    console.log(path);
                    noParkingZoneStore.createNoParkingZone(path, userStore.token);
                    map.remove(polygonEditor.getTarget());
                    polygonEditor.close(true);
                    mapStatusStore.setNoParkingZoneOperationFinishStatus(true);
                    console.log(mapStatusStore.isNoParkingZoneOperationFinish);
                }
            });

            async function getNoParkingZoneData(newValue) {
            if (newValue !== -1) {
                    // 根据传入的记录id获取对应的轨迹数据
                    await noParkingZoneStore.getEditingZoneDataByID(userStore.token);
                    // 根据noParkingZoneStore中的editingNoParkingZoneLocation设置轨迹
                    let path = [];  
                    console.log(noParkingZoneStore.editingNoParkingZoneLocation);
                    for (let i = 0; i < noParkingZoneStore.editingNoParkingZoneLocation.length; i++) {
                        path.push(new AMap.LngLat(noParkingZoneStore.editingNoParkingZoneLocation[i][0], noParkingZoneStore.editingNoParkingZoneLocation[i][1]));
                    }
                    console.log(path);
                    var editingPolygon = new AMap.Polygon({
                        path: path,
                        strokeWeight: 12, //线条宽度，默认为 2
                        strokeColor: "red", //线条颜色
                        fillColor: "#ff73b3", //多边形填充颜色
                    });
                    // 创建对应的编辑器
                    map.add(editingPolygon);
                    polygonEditor = new AMap.PolygonEditor(map, editingPolygon);
                    polygonEditor.open();
                } else {
                    const path = polygonEditor.getTarget()._opts.path;
                    console.log(path);
                    noParkingZoneStore.upDateNoParkingZone(path, userStore.token);
                    map.remove(polygonEditor.getTarget());
                    polygonEditor.clearAdsorbPolygons();
                    polygonEditor.close();
                    map.clearMap();
                    mapStatusStore.setNoParkingZoneOperationFinishStatus(true);
                    console.log(mapStatusStore.isNoParkingZoneOperationFinish);
                }
            }

            // 监视是否处于编辑状态
            watch(() => noParkingZoneStore.editingNoParkingZone, (newValue, oldValue) => {
                console.log(newValue);
                // 根据要编辑的记录id获取对应的轨迹数据
                getNoParkingZoneData(newValue);

            });


            // polygon数组，用于存储所有的多边形边界
            let polygons = [];
            let polygonid = [];

            // 将轨迹数据转换为需要的格式, 根据传入的记录id确定对应的轨迹
            const initializeZoneDataMask = (zoneId) => {
                // 利用zoneId获取在noParkingZoneStore中的对应的轨迹数据
                let zonesides = noParkingZoneStore.getZoneSidesByID(zoneId);
                console.log(zonesides);
                // 清空path数组
                let path = [];
                for (let i = 0; i < zonesides.edges.length; i++) {
                    console.log(zonesides.edges[i][0], zonesides.edges[i][1]);
                    path.push(new AMap.LngLat(zonesides.edges[i][0], zonesides.edges[i][1]));
                }
                console.log(path);
                //创建 polygon 实例
                var polygon = new AMap.Polygon({
                    path: path,
                    fillColor: "#ff73b3", //多边形填充颜色
                    strokeWeight: 2, //线条宽度，默认为 2
                    strokeColor: "red", //线条颜色
                });
                // 将轨迹添加到地图上
                map.add(polygon);
                polygons.push(polygon);
                // 将轨迹的id添加到polygonid数组中
                polygonid.push(zoneId);
            }

            // 监听noParkingZoneStore中的visibleNew变量,当值不为-1时，设置轨迹
            watch(() => noParkingZoneStore.visibleNew, (newValue, oldValue) => {
                console.log("visibleNew", newValue);
                if (newValue !== -1) {
                    initializeZoneDataMask(newValue);
                    // 将visibleNew设置为-1
                    noParkingZoneStore.setNewVisible(-1);
                }
            });

            // 根据传入的记录id删除对应的轨迹
            const removeZoneDataMask = (zoneId) => {
                let index = polygonid.indexOf(zoneId);
                if (index !== -1) {
                    map.remove(polygons[index]);
                    polygons.splice(index, 1);
                    polygonid.splice(index, 1);
                }
            }

            // 监听noParkingZoneStore中的removeOld变量,当值不为-1时，删除轨迹
            watch(() => noParkingZoneStore.removeOld, (newValue, oldValue) => {
                console.log("removeOld", newValue);
                if (newValue !== -1) {
                    removeZoneDataMask(newValue);
                    // 将removeOld设置为-1
                    noParkingZoneStore.setRemoveOld(-1);
                }
            });

            // 监听是否删除操作完成
            watch(() => noParkingZoneStore.deleteId, (newValue, oldValue) => {
                console.log(newValue);
                if (newValue !== -1){
                    // 根据传入的记录id删除对应的轨迹
                    removeZoneDataMask(newValue);
                    // 将removeOld设置为-1
                    noParkingZoneStore.setDeleteId(-1);
                }
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
