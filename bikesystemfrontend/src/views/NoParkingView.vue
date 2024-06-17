<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:20:35
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-17 21:00:10
-->

<template>
  <div style="display: flex; height: 100vh;">
    <div style="width: 30%; background-color:azure;">
      <Table :tableConfig="noParkingZoneTableConfig">
      </Table>
    </div>
    <div style="flex-grow: 1; display: flex; flex-direction: column;">
      <div class="toolbar" style="background-color: lightblue; flex-basis: 16%;" v-if="userStore.editAble">
        <el-button size="large" type="primary" @click="createNoParkingZone">创建禁停区</el-button>
        <el-button size="large" type="success" @click="createFinish = true">完成创建</el-button>
        <el-button size="large" type="success" @click="finishEditNoParkingZone">完成编辑</el-button>
        <el-button size="large" type="warning" @click="giveUpOperation">放弃操作</el-button>
        <el-button :type="mapStatusStore.getNoParkingZoneBikeVisibleStatus ? 'info' : 'primary'" size="large"
          @click="showBikeInNPZone">
          改变禁停区内的单车显示状态
        </el-button>
      </div>
      <div v-if="!userStore.editAble"
        style="background-color: lightblue; flex-basis: 16%; color: red; font-size: large;">
        警告：您没有权限进行编辑操作！
      </div>

      <div style="background-color: #ccc; flex-grow: 1;">
        <MapContainerForNoParkingZone></MapContainerForNoParkingZone>
      </div>
    </div>
  </div>
  <div>
    <el-dialog v-model="createFinish" title="请为禁停区命名" width="500" class="center-dialog">
      <el-input v-model="zoneName" autocomplete="off"></el-input>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="createFinish = false">取消创建</el-button>
          <el-button type="primary" @click="finishCreateNoParkingZone">
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>

</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { TableConfig } from '../types/table'
import { useUserStore } from '../stores/user'
import { useMapStatusStore } from '../stores/mapStatus';
import { useNoParkingZoneStore } from '../stores/noParkingZone'
import { onBeforeRouteLeave } from 'vue-router';

const userStore = useUserStore()
const mapStatusStore = useMapStatusStore()
const noParkingZoneStore = useNoParkingZoneStore()
const createFinish = ref(false)
const zoneName = ref('')

function createColumn(prop, label, isEnum, canSort, canEdit?, enumOptions?) {
  if (canEdit === undefined) {
    canEdit = false;
  }
  return {
    prop,
    label,
    isEnum,
    canSort,
    canEdit,
    enumOptions,
    formatter: (row) => `Formatted ${row}`,
    width: '240px',
    style: 'AdminStyle',
    labelStyle: 'AdminLabelStyle',
  };
}

const noParkingZoneTableConfig = reactive({
  api: '/noParkingZones',
  canDelete: userStore.editAble,
  canEdit: userStore.editAble,
  useMap: true,
  columns: [
    createColumn('zoneid', '禁停区ID', false, true),
    createColumn('name', '禁停区名称', false, true),
  ],
  layout: 'bike-Layout',
} as TableConfig);

onBeforeRouteLeave(() => {
  // 所有涉及的mapStatusStore都置零
  mapStatusStore.setForNoParkingZoneCreateStatus(false);
  mapStatusStore.setForNoParkingZoneEditStatus(false);
  // 离开页面时，将新建的禁停区名字置空
  noParkingZoneStore.newNoparkingZoneName = '';
  // 销毁地图
  mapStatusStore.setMapDistroiedStatus(true);
});

// 补充点击事件
function createNoParkingZone() {
  mapStatusStore.setForNoParkingZoneCreateStatus(true);
}

function finishCreateNoParkingZone() {
  noParkingZoneStore.newNoparkingZoneName = zoneName.value;
  console.log(noParkingZoneStore.newNoparkingZoneName);
  // 设置对话框消失
  createFinish.value = false;
  mapStatusStore.setForNoParkingZoneCreateStatus(false);
}

function finishEditNoParkingZone() {
  mapStatusStore.setForNoParkingZoneEditStatus(false);
}

function showBikeInNPZone() {
  const status = mapStatusStore.getNoParkingZoneBikeVisibleStatus;
  console.log("showstatus:"+status);
  mapStatusStore.setNoParkingZoneBikeVisibleStatus(!status);
}

function giveUpOperation() {
  mapStatusStore.setGiveUpNoParkingZoneOperationStatus(true);
}

</script>

<style>
.content {
  padding: 20px;
  background-color: azure;
}

.toolbox {
  background-color: #545c64;
  color: #fff;
}

.toolbar {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 10px;
}
</style>