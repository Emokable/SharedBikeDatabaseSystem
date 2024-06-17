<template>
    <el-dialog v-model="statusStore.isSelectBikeByLocation" title="请选择时间范围" width="500">
        <div class="time-select">
            <el-form>
                <el-form-item label="经度范围">
                    <el-col :span="11">
                        <el-input v-model="startX" placeholder="起始经度"></el-input>
                    </el-col>
                    <el-col class="line" :span="2">-</el-col>
                    <el-col :span="11">
                        <el-input v-model="endX" placeholder="终止经度"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="纬度范围">
                    <el-col :span="11">
                        <el-input v-model="startY" placeholder="起始纬度"></el-input>
                    </el-col>
                    <el-col class="line" :span="2">-</el-col>
                    <el-col :span="11">
                        <el-input v-model="endY" placeholder="终止纬度"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
        </div>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="statusStore.isSelectBikeByLocation = false">取消</el-button>
                <el-button type="primary" @click="searchByLocation">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>

    <el-dialog v-model="dialogTableVisible" title="区域内空闲单车" width="1200">
        <el-table :data="gridData.values" height=400>
            <el-table-column prop="bikeid" label="单车ID" width="180"></el-table-column>
            <el-table-column prop="brand" label="单车品牌" width="180"></el-table-column>
            <el-table-column prop="status" label="单车状态" width="180"></el-table-column>
            <el-table-column prop="releasedate" label="投放日期" width="180"></el-table-column>
            <el-table-column prop="warrantyPeriod" label="保修时长" width="180"></el-table-column>
            <el-table-column prop="lastusetime" label="最后使用时间" width="180"></el-table-column>
            <el-table-column prop="locationX" label="投放地点_经度" width="180"></el-table-column>
            <el-table-column prop="locationY" label="投放地点_纬度" width="180"></el-table-column>
        </el-table>
    </el-dialog>


</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import { useStatusStore } from '../stores/operationStatus';
import { useUserStore } from '../stores/user';
import { bikeData } from '../types/bike';
import { http } from '../utils/http'

const statusStore = useStatusStore()
const userStore = useUserStore()

const dialogTableVisible = ref(false)
const startX = ref(0)
const startY = ref(0)
const endX = ref(0)
const endY = ref(0)

const gridData = reactive({
    values: [] as bikeData[]
})

async function searchByLocation() {
    // 调用http的getBikeByLocation，将返回的数据赋值给gridData
    await http.getBikeByLocation("/bikes/location", userStore.token, startX.value, startY.value, endX.value, endY.value).then((res) => {
        gridData.values = res.data.data.map((item: any) => ({
            ...item,
        }) as bikeData);
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