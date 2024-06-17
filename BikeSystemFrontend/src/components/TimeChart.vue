<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-18 05:10:54
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 05:54:30
-->
<template>
    <!-- 热点时段选择对话框 -->
    <el-dialog v-model="statusStore.isShowHotTimes" title="请选择时间范围" width="500">
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
                <el-button @click="statusStore.isShowHotTimes = false">取消</el-button>
                <el-button type="primary" @click="getHotTime">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>

    <!-- 热点时段统计对话框 -->
    <el-dialog v-model="dialogTableVisible" title="热点时段一览" width="1200" @opened="initializeChart">
        <div class="lineChart">
            <div id="lineChart" style="width: 1200px; height: 500px;"></div>
        </div>
    </el-dialog>
</template>

<script lang="ts" setup>
import { ref, reactive, watch, nextTick } from 'vue'
import { useStatusStore } from '../stores/operationStatus';
import { useUserStore } from '../stores/user';
import { http } from '../utils/http'
import * as echarts from 'echarts';

interface recordData {
    hour: number,
    ride_count: number
}

const statusStore = useStatusStore()
const userStore = useUserStore()

const value = ref('')
const value2 = ref('')
const dialogTableVisible = ref(false)
const defaultTime = new Date(2016, 8, 15, 12, 0, 0)

const gridData = reactive({
    values: [] as recordData[]
})

async function getHotTime() {
    await http.getHotTime(userStore.token, value.value, value2.value).then((res) => {
        gridData.values = res.data.data.map((item: any) => ({
            ...item,
        }) as recordData);
        console.log(gridData)
    })

    dialogTableVisible.value = true
}

// 图表初始化相关变量
let myChart: echarts.ECharts | null = null;

// 配置项函数
const chartOption = (data: recordData[]) => ({
    title: {
        text: '骑行热点时段统计图'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['骑行记录数']
    },
    xAxis: {
        type: 'category',
        boundaryGap: true,
        data: data.map((item) => item.hour)
    },
    yAxis: {
        type: 'value',
    },
    series: [{
        name: '骑行记录数',
        type: 'bar',
        smooth: true,
        data: data.map((item) => item.ride_count)
    }]
});

// 初始化图表
function initializeChart() {
    nextTick(() => {
        if (!myChart) {
            const chartDom = document.getElementById('lineChart');
            if (chartDom) {
                myChart = echarts.init(chartDom);
            }
        }
        if (myChart) {
            myChart.setOption(chartOption(gridData.values));
        }
    });
}

// 监听 dialogTableVisible 变化，清理图表
watch(dialogTableVisible, (newVal) => {
    if (!newVal && myChart) {
        myChart.clear();
    }
});

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