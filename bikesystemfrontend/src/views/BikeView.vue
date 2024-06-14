<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:06:31
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-14 23:06:10
-->
<template>
  <div class="bike-layout">
      <el-container>
          <el-aside width="200px" class="toolbox">
          </el-aside>
          <el-container>
              <el-main class = 'content'>
                  <Table :tableConfig="bikeTableConfig">
                  </Table>
              </el-main>
          </el-container>
      </el-container>
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

const bikeTableConfig = reactive({
  api: '/bikes',
  canDelete: false,
  canEdit: userStore.editAble,
  columns: [
      createColumn('bikeid', '单车ID', false, true),
      createColumn('brand', '单车品牌', false, true),
      createColumn('status', '单车状态', true, true, true, ['available', 'locked', 'damaged']),
      createColumn('releasedate', '投放日期', false, true),
      createColumn('warrantyPeriod', '保修时长', false, true),
      createColumn('lastusetime', '最后使用时间', false, true),
      createColumn('locationX', '投放地点_经度', false, true),
      createColumn('locationY', '投放地点_纬度', false, true),
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