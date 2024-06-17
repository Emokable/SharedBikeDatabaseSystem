<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:20:00
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-17 16:28:07
-->

<template>
    <div class="rider-layout">
        <el-container>
            <el-aside width="200px" class="toolbox"
                style="display: flex; flex-direction: column; justify-content: space-around;">
                <template v-if="userStore.editAble">
                    <el-button size="large" type="primary" @click="dialogFormVisible = true">
                        注册新的用户
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
                    <Table :tableConfig="riderTableConfig">
                    </Table>
                </el-main>
            </el-container>
        </el-container>
    </div>

      
  <div class="edit-form">
        <el-dialog v-model="dialogFormVisible" title="请编辑对应信息" width="500">
            <template v-if="dialogFormVisible">
                <InsertForm :tableConfig="riderTableConfig" :formData="emptyData"></InsertForm>
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
    useMap: false,
    columns: [
        createColumn('userid', '骑行者ID', false, true),
        createColumn('avatar', '骑行者头像', false, false),
        createColumn('username', '骑行者名称', false, true),
        createColumn('gender', '骑行者性别', true, true, ['male', 'female', 'other']),
        createColumn('password', '骑行者密码', false, true),
        createColumn('phonenumber', '骑行者电话', false, true),
        createColumn('birthday', '骑行者生日', false, true),
    ],
    layout: 'exampleLayout',
} as TableConfig);

function createEmptyData() {
    return {
        userid: '',
        avatar: '',
        username: '',
        gender: '',
        password: '',
        phonenumber: '',
        birthday: '',
    };
}

const emptyData = reactive(createEmptyData());

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