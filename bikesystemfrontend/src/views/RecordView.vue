<template>
    <div class="rider-layout">
        <el-container>
            <el-aside width="200px" class="toolbox">
            </el-aside>
            <el-container>
                <el-main class = 'content'>
                    <Table :tableConfig="riderTableConfig">
                    </Table>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script lang="ts" setup>
import { reactive } from 'vue'
import { TableConfig } from '../types/table'

function createColumn(prop, label, isEnum, canSort, enumOptions? ) {
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


const riderTableConfig = reactive({
    api: '/rideRecords',
    canDelete: false,
    canEdit: false,
    columns: [
        createColumn('orderid', '订单ID', false, true),
        createColumn('bikeid', '单车ID', false, true),
        createColumn('userid', '骑行者ID', false, true),
        createColumn('starttime', '开始时间', false, true),
        createColumn('startlocationx', '起点X坐标', false, true),
        createColumn('startlocationy', '起点Y坐标', false, true),
        createColumn('endtime', '结束时间', false, true),
        createColumn('endlocationx', '终点X坐标', false, true),
        createColumn('endlocationy', '终点Y坐标', false, true),
        createColumn('track', '轨迹', false, true),
    ],
    layout: 'exampleLayout',
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