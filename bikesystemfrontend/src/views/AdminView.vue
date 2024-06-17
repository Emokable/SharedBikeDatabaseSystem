<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 19:56:35
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-17 16:27:40
-->
<template>
    <div class="rider-layout">
        <el-container>
            <el-aside width="200px" class="toolbox"
                style="display: flex; flex-direction: column; justify-content: space-around;">
                <template v-if="userStore.isSuperuser">
                    <el-button size="large" type="primary" @click="dialogFormVisible = true">
                        注册新的用户
                    </el-button>
                </template>
                <template v-else>
                    <div
                        style="background-color: lightblue; color: red; font-size: large; text-align: center; padding: 10px;">
                        警告：您没有权限进行对应不同用户进行操作
                    </div>
                </template>
            </el-aside>
            <el-container>
                <el-main class='content'>
                    <Table :tableConfig="adminTableConfig">
                    </Table>
                </el-main>
            </el-container>
        </el-container>
    </div>

    <div class="edit-form">
        <el-dialog v-model="dialogFormVisible" title="请编辑对应信息" width="500">
            <template v-if="dialogFormVisible">
                <InsertForm :tableConfig="adminTableConfig" :formData="emptyData"></InsertForm>
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

function createColumn(prop, label, isEnum, canSort, enumOptions?) {
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
    canDelete: userStore.isSuperuser,
    canEdit: userStore.isSuperuser,
    useMap: false,
    columns: [
        createColumn('adminid', '管理员ID', false, true),
        createColumn('privileges', '权限', true, false, ['superuser', 'data_modification', 'read_only']),
        createColumn('username', '用户名', false, true),
        createColumn('gender', '性别', true, true, ['male', 'female', 'other']),
        createColumn('phonenumber', '手机号', false, true),
        createColumn('avatar', '头像', false, false),
        createColumn('birthday', '生日', false, true),
    ],
    layout: 'exampleLayout',
} as TableConfig);


// 根据adminData的格式，生成一个新的空白对象
function createEmptyAdmin() {
    return {
        userid: 0,
        username: '',
        gender: '',
        password: '',
        phoneNumber: '',
        avatar: '',
        birthday: '',
        authorityLevel: ''
    };
};

const emptyData = reactive(createEmptyAdmin());

</script>

<style>
.content {
    padding: 20px;
    background-color: azure;
}

.toolbox {
    background-color: #545c64;
    color: #fff;
    display: flex;
}
</style>