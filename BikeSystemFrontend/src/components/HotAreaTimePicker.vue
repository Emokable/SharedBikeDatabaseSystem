<!-- 
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-18 06:42:12
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 06:44:26
-->
<!-- <template>
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


</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import { useStatusStore } from '../stores/operationStatus';
import { useUserStore } from '../stores/user';
import { Location, useMapStatusStore } from '../stores/mapStatus';
import { recordData } from '../types/record';
import { http } from '../utils/http'

const statusStore = useStatusStore()
const userStore = useUserStore()
const mapStore = useMapStatusStore()

const value = ref('')
const value2 = ref('')
const dialogTableVisible = ref(false)
const defaultTime = new Date(2016, 8, 15, 12, 0, 0)

const gridData = reactive({
    values: [] as Location[]
})

async function getSchedulingPlan() {
    await http.getSchedulePlan(userStore.token, value.value, value2.value).then((res) => {
        //"data": [
        // {
        //   "121.51,31.30": 29
        // },
        // {
        //   "121.53,31.32": 25
        // },
        // {
        //   "121.48,31.20": 20
        // },
        // 将以上数据转换为Location类型
        // 
        


    })
}

// </script>

// <style scoped>
// .time-select {
//     display: flex;
//     flex-direction: row;
//     align-items: center;
//     justify-content: center;
//     margin-top: 20px;
// }

// .datetime-picker {
//     display: flex;
//     width: 100%;
//     padding: 0;
//     flex-wrap: wrap;
// }

// .demonstration {
//     margin-right: 10px;
//     font-size: 14px;
//     line-height: 40px;
// }
// </style> -->