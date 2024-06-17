<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:20:00
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 03:09:31
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
    <el-dialog v-model="dialogFormVisible" title="请输入数据对应ID" width="500">
        <!-- Directly place el-input inside the el-dialog content area -->
        <el-input v-model="emptyData.userid" placeholder="请输入用户ID"></el-input>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="checkID">
                    确认ID
                </el-button>
            </div>
        </template>
    </el-dialog>
    <el-dialog v-model="dialogFormVisible2" title="请编辑对应信息" width="500">
        <!-- Use v-if directly on the component you want to conditionally render -->
        <InsertForm v-if="dialogFormVisible2" :tableConfig="riderTableConfig" :formData="emptyData"></InsertForm>
    </el-dialog>
</div>
</template>

<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import { TableConfig } from '../types/table'
import { useUserStore } from '../stores/user'
import { useStatusStore } from '../stores/operationStatus';
import { http } from '../utils/http'

const userStore = useUserStore()
const dialogFormVisible = ref(false)
const dialogFormVisible2 = ref(false)
const statusStore = useStatusStore()

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
        userid: 0,
        avatar: '',
        username: '',
        gender: '',
        password: '',
        phonenumber: '',
        birthday: '',
    };
}

var emptyData = reactive(createEmptyData())

// 检查输入的ID是否已经重复
function checkID() {
    if (emptyData.userid === 0) {
        window.alert('请输入ID');
    } else {
        http.getByID('/riders', userStore.token, emptyData.userid)
            .then((res) => {
                if (res.data.data === null) {
                    dialogFormVisible2.value = true;
                    dialogFormVisible.value = false;
                } else {
                    window.alert('ID已存在，请重新输入');
                }
            })
            .catch(() => {
                window.alert('ID检查失败');
            });
    }
}

watch(() => statusStore.isCreateFinish, (newValue, oldValue) => {
    if (newValue) {
        dialogFormVisible2.value = false
        // 重置数据
        emptyData = reactive(createEmptyData());
    }
})

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