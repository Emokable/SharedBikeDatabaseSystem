<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:06:47
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-17 22:36:00
-->
<template>
    <div class="record-layout">
        <el-container>
            <el-aside width="170px" class="toolbox"
                style="display: flex; flex-direction: column; justify-content: space-around;">
                <template v-if="userStore.isSuperuser">
                    <el-button size="large" type="primary" @click="dialogFormVisible = true">
                        登记新的骑行记录
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
    <div class="edit-form">
        <el-dialog v-model="dialogFormVisible" title="请输入数据对应orderID, bikeID, userID" width="500">
            <!-- Directly place el-input inside the el-dialog content area -->
            <el-form label-width="auto" style="max-width: 600px">
                <el-form-item label="记录ID：">
                    <el-input v-model="emptyData.orderid" placeholder="请输入记录ID"></el-input>
                </el-form-item>
                <el-form-item label="用户ID：">
                    <el-input v-model="emptyData.userid" placeholder="请输入用户ID"></el-input>
                </el-form-item>
                <el-form-item label="单车ID：">
                    <el-input v-model="emptyData.bikeid" placeholder="请输入单车ID"></el-input>
                </el-form-item>
            </el-form>
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
            <InsertForm v-if="dialogFormVisible2" :tableConfig="recordTableConfig2" :formData="emptyData"></InsertForm>
        </el-dialog>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import { TableConfig } from '../types/table'
import { useUserStore } from '../stores/user'
import { useStatusStore } from '../stores/operationStatus';
import { http } from '../utils/http'
import { useMapStatusStore } from '../stores/mapStatus';
import { onBeforeRouteLeave } from 'vue-router';


const userStore = useUserStore()
const dialogFormVisible = ref(false)
const dialogFormVisible2 = ref(false)
const statusStore = useStatusStore()
const mapStatusStore = useMapStatusStore()

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

const recordTableConfig2 = reactive({
    api: '/rideRecords',
    canDelete: false,
    canEdit: false,
    useMap: true,
    columns: [
        createColumn('orderid', '订单ID', false, true),
        createColumn('bikeid', '单车ID', false, true),
        createColumn('userid', '骑行者ID', false, true),
        createColumn('starttime', '开始时间', false, true),
        createColumn('endtime', '结束时间', false, true),
        createColumn('startlocationx', '开始地点_经度', false, true),
        createColumn('startlocationy', '开始地点_纬度', false, true),
        createColumn('endlocationx', '结束地点_经度', false, true),
        createColumn('endlocationy', '结束地点_纬度', false, true),
        createColumn('track', '骑行轨迹', false, true)
    ],
    layout: 'exampleLayout',
} as TableConfig);

// 根据adminData的格式，生成一个新的空白对象
function createEmptyRecord() {
    return {
        orderid: 0,
        bikeid: 0,
        userid: 0,
        starttime: '',
        endtime: '',
        startlocationx: 0,
        startlocationy: 0,
        endlocationx: 0,
        endlocationy: 0,
        track: '',
    };
};

var emptyData = reactive(createEmptyRecord());

// 检查输入的ID是否已经重复，以及是否有对应的用户和单车
async function checkID() {
    if (emptyData.bikeid === 0 || emptyData.userid === 0 || emptyData.orderid === 0) {
        window.alert('请输入ID');
    } else {
        const flag1 = ref(false)
        const flag2 = ref(false)
        const flag3 = ref(false)
        try {
            const bikeResponse = await http.getByID('/bikes', userStore.token, emptyData.bikeid);
            if (bikeResponse.data.data === null) {
                window.alert('单车不存在，请重新输入');
            } else {
                flag1.value = true;
            }
        } catch (error) {
            window.alert('ID检查失败');
        }

        try {
            const riderResponse = await http.getByID('/riders', userStore.token, emptyData.userid);
            if (riderResponse.data.data === null) {
                window.alert('用户不存在，请重新输入');
            } else {
                flag2.value = true;
            }
        } catch (error) {
            window.alert('ID检查失败');
        }

        try {
            const recordResponse = await http.getByID('/rideRecords', userStore.token, emptyData.orderid);
            if (recordResponse.data.data === null) {
                flag3.value = true;
            } else {
                window.alert('记录已存在，请重新输入编号');
            }
        } catch (error) {
            window.alert('ID检查失败');
        }

        console.log(flag1.value, flag2.value, flag3.value);

        if (flag1.value && flag2.value && flag3.value) {
            dialogFormVisible2.value = true;
            dialogFormVisible.value = false;
        }
    }

    watch(() => statusStore.isCreateFinish, (newValue, oldValue) => {
        if (newValue) {
            dialogFormVisible2.value = false
            // 重置数据
            emptyData = reactive(createEmptyRecord());
        }
    })
}

    onBeforeRouteLeave(() => {
        mapStatusStore.setMapDistroiedStatus(true);
    })

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