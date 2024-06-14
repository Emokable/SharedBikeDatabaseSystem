<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:20:00
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-14 18:02:55
-->
<template>
    <div class="bike-layout">
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
        style: 'RiderStyle',
        labelStyle: 'RiderLabelStyle',
    };
}

const riderTableConfig = reactive({
    api: '/riders',
    canDelete: false,
    canEdit: false,
    columns: [
        createColumn('userid', '骑行者ID', false, true),
        createColumn('avator', '骑行者头像', false, false),
        createColumn('username', '骑行者名称', false, true),
        createColumn('gender', '骑行者性别', true, true, ['male', 'female', 'other']),
        createColumn('password', '骑行者密码', false, true),
        createColumn('phonenumber', '骑行者电话', false, true),
        createColumn('birthday', '骑行者生日', false, true),
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