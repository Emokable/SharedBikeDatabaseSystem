<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 19:56:35
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-14 21:33:18
-->
<template>
    <div class="rider-layout">
        <el-container>
            <el-aside width="200px" class="toolbox">
            </el-aside>
            <el-container>
                <el-main class = 'content'>
                    <Table :tableConfig="adminTableConfig">
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

const userStore = useUserStore()

function createColumn(prop, label, isEnum, canSort, enumOptions? ) {
    return {
        prop,
        label,
        isEnum,
        canSort,
        enumOptions,
        formatter: (row) => `Formatted ${row}`,
        width: '240px',
        style: 'AdminStyle',
        labelStyle: 'AdminLabelStyle',
    };
}

const adminTableConfig = reactive({
    api: '/admins',
    canDelete: false,
    canEdit: true,
    // **管理员（Admin）**
    // *privileges权限（三类）*
    // *adminid（管理员ID）*
    // *username（用户名）*
    // *gender（性别）*
    // *password（密码）*
    // *phone_number（手机号）*
    // *avatar（头像）*
    // *生日*
    columns: [
        createColumn('privileges', '权限', true, false, ['superuser', 'data_modification', 'read_only']),
        createColumn('adminid', '管理员ID', false, true),
        createColumn('username', '用户名', false, true),
        createColumn('gender', '性别', true, true, ['male', 'female', 'other']),
        createColumn('phonenumber', '手机号', false, true),
        createColumn('avatar', '头像', false, false),
        createColumn('birthday', '生日', false, true),
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