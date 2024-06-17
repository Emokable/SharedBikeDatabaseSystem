<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 21:48:12
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-17 20:43:20
-->
<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 19:56:35
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-17 19:34:32
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
    <el-dialog v-model="dialogFormVisible" title="请输入数据对应ID" width="500">
        <!-- Directly place el-input inside the el-dialog content area -->
        <el-input v-model="emptyData.adminid" placeholder="请输入用户ID"></el-input>
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
        <InsertForm v-if="dialogFormVisible2" :tableConfig="adminTableConfig" :formData="emptyData"></InsertForm>
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
        adminid: 0,
        username: '',
        gender: '',
        password: '654321',
        phonenumber: '',
        avatar: '',
        birthday: '',
        privileges: ''
    };
};


var emptyData = reactive(createEmptyAdmin());

// 检查输入的ID是否已经重复
function checkID() {
    if (emptyData.adminid === 0) {
        window.alert('请输入ID');
    } else {
        http.getByID('/admins', userStore.token, emptyData.adminid)
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
        emptyData = reactive(createEmptyAdmin());;
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
    display: flex;
}
</style>