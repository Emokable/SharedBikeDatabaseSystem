<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:20:35
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-15 12:53:09
-->

<template>
    <div style="display: flex; height: 100vh;">
    <div style="width: 30%; background-color:azure;">
        <Table :tableConfig="noParkingZoneTableConfig">
        </Table>
    </div>
    <div style="flex-grow: 1; display: flex; flex-direction: column;">
        <div style="background-color: lightblue; flex-basis: 20%;">上方容器</div>
        <div style="background-color: #ccc; flex-grow: 1;">
            <MapContainer></MapContainer>
        </div>
    </div>
    </div>
</template>

<script lang="ts" setup>
import { reactive } from 'vue'
import { TableConfig } from '../types/table'
import { useUserStore } from '../stores/user'



function createColumn(prop, label, isEnum, canSort,canEdit?, enumOptions? ) {
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

const userStore = useUserStore()

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
</style>