<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:06:47
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-15 11:38:14
-->
<template>
    <div class="record-layout">
        <el-container>
            <el-aside width="200px" class="toolbox">
            </el-aside>
            <el-container>
                <el-main class='recordContent'>
                    <div style="height: 100%; width: 100%; display: flex; flex-direction: row;">
                        <div style="background-color: lightblue; flex-basis: 50%;">
                            <Table :tableConfig="recordTableConfig">
                            </Table>
                        </div>
                        <div style="flex-grow: 1;">
                            <MapContainer></MapContainer>
                        </div>
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
    useMap: true,
    columns: [
        createColumn('orderid', '订单ID', false, true),
        createColumn('bikeid', '单车ID', false, true),
        createColumn('userid', '骑行者ID', false, true),
        createColumn('startTime', '开始时间', false, true),
        createColumn('endTime', '结束时间', false, true),
    ],
    layout: 'exampleLayout',
} as TableConfig);
</script>

<style>

.toolbox {
    background-color: #545c64;
    color: #fff;
}
.record-layout {
    height: 100%;
}


</style>