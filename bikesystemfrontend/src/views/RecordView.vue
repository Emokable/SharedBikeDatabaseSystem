<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:06:47
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-15 03:16:56
-->
<template>
    <div class="record-layout">
        <el-container>
            <el-aside width="200px" class="toolbox">
            </el-aside>
            <el-container>
                <el-main class='recordContent'>
                    <div class='table'>
                        <Table :tableConfig="recordTableConfig">
                        </Table>
                    </div>
                    <div class='map'>
                    <MapContainer></MapContainer>
                    </div>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script lang="ts" setup>
import { reactive } from 'vue'
import { TableConfig } from '../types/table'
import { useUserStore } from '../stores/user'

function createColumn(prop, label, isEnum, canSort, enumOptions?) {
    return {
        prop,
        label,
        isEnum,
        canSort,
        enumOptions,
        formatter: (row) => `Formatted ${row}`,
        width: '240px',
        style: 'RecordStyle',
        labelStyle: 'RecordLabelStyle',
    };
}

const userStore = useUserStore()

const recordTableConfig = reactive({
    api: '/rideRecords',
    canDelete: false,
    canEdit: false,
    columns: [
        createColumn('orderid', '订单ID', false, true),
        createColumn('bikeid', '单车ID', false, true),
        createColumn('userid', '骑行者ID', false, true),
        createColumn('startTime', '开始时间', false, true),
        createColumn('endTime', '结束时间', false, true),
        createColumn('startLocationX', '起点X坐标', false, true),
        createColumn('startLocationY', '起点Y坐标', false, true),
        createColumn('endLocationX', '终点X坐标', false, true),
        createColumn('endLocationY', '终点Y坐标', false, true),
        createColumn('track', '轨迹', false, true),
    ],
    layout: 'exampleLayout',
} as TableConfig);
</script>

<style>

.toolbox {
    background-color: #545c64;
    color: #fff;
}


</style>