<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:06:31
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-17 16:28:31
-->
<template>
  <div class="bike-layout">
      <el-container>
        <el-aside width="200px" class="toolbox"
                style="display: flex; flex-direction: column; justify-content: space-around;">
                <template v-if="userStore.editAble">
                    <el-button size="large" type="primary" @click="dialogFormVisible = true">
                        注册新的单车
                    </el-button>
                </template>
                <template v-else>
                    <div
                        style="background-color: lightblue; color: red; font-size: large; text-align: center; padding: 10px;">
                        警告：您没有权限进行操作
                    </div>
                </template>
            </el-aside>
          <el-container>
              <el-main class = 'content'>
                  <Table :tableConfig="bikeTableConfig">
                  </Table>
              </el-main>
          </el-container>
      </el-container>
  </div>
  
  <div class="edit-form">
        <el-dialog v-model="dialogFormVisible" title="请编辑对应信息" width="500">
            <template v-if="dialogFormVisible">
                <InsertForm :tableConfig="bikeTableConfig" :formData="emptyData"></InsertForm>
            </template>
        </el-dialog>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import { TableConfig } from '../types/table'
import { useUserStore } from '../stores/user'
import { useStatusStore } from '../stores/operationStatus';

const userStore = useUserStore()
const dialogFormVisible = ref(false)
const statusStore = useStatusStore()

watch(() => statusStore.isCreateFinish, (newValue, oldValue) => {
    if (newValue) {
        dialogFormVisible.value = false
        statusStore.setCreateFinish(false)
    }
})

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
};


const bikeTableConfig = reactive({
  api: '/bikes',
  canDelete: false,
  canEdit: userStore.editAble,
  useMap: false,
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

// 根据adminData的格式，生成一个新的空白对象
function createEmptyBike() {
    return {
        bikeid: '',
        brand: '',
        status: '',
        releasedate: '',
        warrantyPeriod: '',
        lastusetime: '',
        locationX: '',
        locationY: '',
    };
};

const emptyData = reactive(createEmptyBike());


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