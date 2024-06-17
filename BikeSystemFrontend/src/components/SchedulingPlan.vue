<template>
    <el-dialog v-model="statusStore.isGetingSchedule" title="请选择时间范围" width="500">
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
                <el-button @click="statusStore.isGetingSchedule = false">取消</el-button>
                <el-button type="primary" @click="getSchedulingPlan">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>

    <el-dialog v-model="dialogTableVisible" title="调度方案一览" width="1200" >
    <el-table :data="gridData.values" height= 400>
        <el-table-column prop="startLocation" label="起始地点" width="180"></el-table-column>
        <el-table-column prop="endLocation" label="目标地点" width="180"></el-table-column>
        <el-table-column prop="timeSlot" label="执行时间" width="180"></el-table-column>
        <el-table-column prop="bikesMoved" label="调度单车数" width="180"></el-table-column>
        <el-table-column prop="completed" label="是否完成" width="180"></el-table-column>
        <el-table-column prop="reason" label="调度理由" width="180"></el-table-column>
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

async function getSchedulingPlan() {
    await http.getSchedulePlan(userStore.token, value.value, value2.value).then((res) => {
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