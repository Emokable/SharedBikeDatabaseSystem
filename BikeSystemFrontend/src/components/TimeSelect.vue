<template>
    <el-dialog v-model="statusStore.isSelectRecordByTime" title="请选择时间范围" width="500">
        <div class="time-select">
            <div class="datetime-picker">
                <span class="demonstration">起始时间点</span>
                <el-date-picker v-model="value" type="datetime" placeholder="Select date and time"
                    :default-time="defaultTime" value-format="YYYY-MM-DD HH:mm:ss" />
            </div>
            <div class="datetime-picker">
                <span class="demonstration">终止时间点</span>
                <el-date-picker v-model="value2" type="datetime" placeholder="Select date and time"
                    :default-time="defaultTime" value-format="YYYY-MM-DD HH:mm:ss" />
            </div>
        </div>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="statusStore.isSelectRecordByTime = false">取消</el-button>
                <el-button type="primary" @click="searchByTime">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>

    <el-dialog v-model="dialogTableVisible" title="时间段内骑行记录" width="1200" >
    <el-table :data="gridData.values" height= 400>
        <el-table-column prop="orderid" label="订单ID" width="180"></el-table-column>
        <el-table-column prop="bikeid" label="单车ID" width="180"></el-table-column>
        <el-table-column prop="userid" label="骑行者ID" width="180"></el-table-column>
        <el-table-column prop="startTime" label="开始时间" width="180"></el-table-column>
        <el-table-column prop="endTime" label="结束时间" width="180"></el-table-column>
        <el-table-column prop="startLocationX" label="开始地点_经度" width="180"></el-table-column>
        <el-table-column prop="startLocationY" label="开始地点_纬度" width="180"></el-table-column>
        <el-table-column prop="endLocationX" label="结束地点_经度" width="180"></el-table-column>
        <el-table-column prop="endLocationY" label="结束地点_纬度" width="180"></el-table-column>
    </el-table>
  </el-dialog>

    
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import { useStatusStore } from '../stores/operationStatus';
import { useUserStore } from '../stores/user';
import { recordData } from '../types/record';
import { http } from '../utils/http'

const statusStore = useStatusStore()
const userStore = useUserStore()

const value = ref('')
const value2 = ref('')
const dialogTableVisible = ref(false)
const defaultTime = new Date(2016, 8, 15, 12, 0, 0)

const gridData = reactive({
    values: [] as recordData[]
})

async function searchByTime() {
    // 调用http的getRecordByTime，传入value.value和value2.value，将返回的数据赋值给gridData
    await http.getRecordByTime("/rideRecords/RrcBtdate", userStore.token, value.value, value2.value).then((res) => {
        gridData.values = res.data.data.map((item: any) => ({
            ...item,
        }) as recordData);
        console.log(gridData)
        dialogTableVisible.value = true
    })
}

</script>

<style scoped>
.time-select {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    margin-top: 20px;
}

.datetime-picker {
    display: flex;
    width: 100%;
    padding: 0;
    flex-wrap: wrap;
}

.demonstration {
    margin-right: 10px;
    font-size: 14px;
    line-height: 40px;
}
</style>