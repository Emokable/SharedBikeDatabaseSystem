<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:20:00
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-13 20:29:26
-->
<template>
    <div class="bike-layout">
        <el-container>
            <el-aside width="200px" class="toolbox">
                Aside
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

function createColumn(prop, label) {
    return {
        prop,
        label,
        formatter: (row) => `Formatted ${row}`,
        width: '240px',
        style: 'RiderStyle',
        labelStyle: 'RiderLabelStyle',
    };
}

const riderTableConfig = reactive({
    api: '/riders',
    belongsTo: 'riders',
    columns: [
        createColumn('userid', '骑行者ID'),
        createColumn('avator', '骑行者头像'),
        createColumn('username', '骑行者名称'),
        createColumn('gender', '骑行者性别'),
        createColumn('password', '骑行者密码'),
        createColumn('phonenumber', '骑行者电话'),
        createColumn('birthday', '骑行者生日'),
    ],
    layout: 'exampleLayout',
} as TableConfig);
</script>

<style>
.content {
    padding: 20px;
}
.toolbox {
    background-color: #545c64;
    color: #fff;
}
</style>