<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:06:31
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 11:55:34
-->
<template>
    <div class="bike-layout">
        <el-container>
            <el-aside width="200px" class="toolbox"
                style="display: flex; flex-direction: column; justify-content: space-around;">
                <el-button size="large" type="primary" @click="statusStore.setSelectBikeByLocation(true)">
                    根据经纬度寻找空闲单车
                </el-button>
                <template v-if="userStore.editAble">
                    <el-button size="large" type="primary" @click="dialogFormVisible = true">
                        注册新的单车
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
                <el-main class='content'>
                    <Table :tableConfig="bikeTableConfig">
                    </Table>
                </el-main>
            </el-container>
        </el-container>
    </div>

    <div class="loglin-select">
        <LocationSelect v-if="statusStore.isSelectBikeByLocation"></LocationSelect>
    </div>

    <div class="edit-form">
        <el-dialog v-model="dialogFormVisible" title="请输入数据对应ID" width="500">
            <!-- Directly place el-input inside the el-dialog content area -->
            <el-input v-model="emptyData.bikeid" placeholder="请输入用户ID"></el-input>
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
            <InsertForm v-if="dialogFormVisible2" :tableConfig="bikeTableConfig" :formData="emptyData"></InsertForm>
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

function createColumn(prop, label, isEnum,  isTime, enumOptions?) {
    return {
        prop,
        label,
        isEnum,
        isTime,
        enumOptions,
        formatter: (row) => `Formatted ${row}`,
        width: '240px',
        style: 'AdminStyle',
        labelStyle: 'AdminLabelStyle',
    };
};


const bikeTableConfig = reactive({
    api: '/bikes',
    canDelete: false,
    canEdit: userStore.editAble,
    useMap: false,
    columns: [
        createColumn('bikeid', '单车ID', false, false),
        createColumn('brand', '单车品牌', false, false, false),
        createColumn('status', '单车状态', true, false, ['unlocked', 'locked', 'damaged']),
        createColumn('releasedate', '投放日期', false,true),
        createColumn('warrantyPeriod', '保修时长', false, false),
        createColumn('lastusetime', '最后使用时间', false,true ),
        createColumn('locationX', '投放地点_经度', false, false ),
        createColumn('locationY', '投放地点_纬度', false, false),
    ],
    layout: 'bike-Layout',
} as TableConfig);

// 根据adminData的格式，生成一个新的空白对象
function createEmptyBike() {
    return {
        bikeid: 0,
        brand: '',
        status: '',
        releasedate: '',
        warrantyPeriod: '',
        lastusetime: '',
        locationX: '',
        locationY: '',
    };
};

var emptyData = reactive(createEmptyBike());

// 检查输入的ID是否已经重复
function checkID() {
    if (emptyData.bikeid === 0) {
        window.alert('请输入ID');
    } else {
        http.getByID('/bikes', userStore.token, emptyData.bikeid)
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
        emptyData = reactive(createEmptyBike());
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