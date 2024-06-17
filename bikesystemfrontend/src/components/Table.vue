<template>
    <div class="table">
        <el-table :data="tableData.values" :table-layout="'auto'" v-loading="loading" :stripe="true" :height="800"
            style="max-width: 1800px" @sort-change="handleSort">
            <el-table-column>
                <template #header>
                    <div class="mt-4">
                        <el-input v-model="searchInput" placeholder="输入具体值" class="input-with-select">
                            <template #prepend>
                                <el-select v-model="searchColumn" placeholder="选择属性" style="min-width: 115px">
                                    <el-option v-for="item in props.tableConfig.columns" :value="item.prop"
                                        :label="item.label" />
                                </el-select>
                            </template>
                        </el-input>
                        <el-button type="primary" @click="handleSearch" :disabled="!searchColumn || !searchInput"> 搜索
                        </el-button>
                        <el-button type="warning" @click="handleResetSearch" :disabled="!searchColumn || !searchInput">
                            撤销 </el-button>
                    </div>
                </template>

                <el-table-column>
                    <el-table-column v-for="item in props.tableConfig.columns" :key="item.prop" :prop="item.prop"
                        :label="item.label" :sortable="'custom'" :fit=true>
                        <!-- 表头自定义 -->
                        <template #header="{ column }">
                            <slot :name="item.prop + 'Header'">
                                <div class="inline-flex" :style="item.labelStyle">
                                    <span>{{ item.label }}</span>
                                </div>
                            </slot>
                        </template>
                    </el-table-column>

                    <el-table-column align="right" width="300" fixed="right">
                        <template #default="scope">
                            <el-button size="small" type='primary' @click="handleEdit(scope.row)"
                                v-if="props.tableConfig.canEdit">
                                Edit
                            </el-button>
                            <el-button size="small" type="danger" @click="handleDelete(scope.row[getFirstColumnProp()])"
                                v-if="props.tableConfig.canDelete">
                                Delete
                            </el-button>
                            <template v-if="props.tableConfig.useMap">
                                <el-button type='success' size="small" @click="handleMAP(scope.row)">
                                    在地图上查看
                                </el-button>
                                <el-button type='warning' size="small" @click="handleNOMAP(scope.row)">
                                    从地图上消除
                                </el-button>
                            </template>
                        </template>
                    </el-table-column>
                </el-table-column>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="pagination.currentPage" :page-sizes="[20, 40, 60, 80]" :page-size="pagination.pageSize"
            layout="total, sizes, prev, pager, next, jumper" :total="tableDataTotal">
        </el-pagination>
    </div>

    <div class="edit-form">
        <el-dialog v-model="dialogFormVisible" title="请编辑对应信息" width="500">
            <template v-if="dialogFormVisible">
                <EditForm :tableConfig="props.tableConfig" :formData="formData"></EditForm>
            </template>
        </el-dialog>
    </div>

</template>

<script lang="ts" setup>
import { computed, ref, reactive, watch } from 'vue'
import { http } from '../utils/http'
import { TableConfig } from '../types/table'
import { riderData } from '../types/rider'
import { adminData } from '../types/admin'
import { bikeData } from '../types/bike'
import { noParkingZoneData } from '../types/noParkingZone'
import { recordData } from '../types/record'
import { useUserStore } from '../stores/user'
import { useStatusStore } from '../stores/operationStatus'
import { useRecordStore } from '../stores/records'
import { useNoParkingZoneStore } from '../stores/noParkingZone'
import { useMapStatusStore } from '../stores/mapStatus'

const dialogFormVisible = ref(false)
const pagination = ref({
    currentPage: 1,
    pageSize: 20
})

// 通过props获取表格配置, 通过store获取token
const props = defineProps<{
    tableConfig: TableConfig
}>()
const userStore = useUserStore()
const statusStore = useStatusStore()
const mapStatusStore = useMapStatusStore();
const noParkingZoneStore = useNoParkingZoneStore()


// 通过http的getTotality方法获取总数
const tableDataTotal = ref<number>(0)
const getTotality = async () => {
    const res = await http.getCount(props.tableConfig.api, userStore.token)
    tableDataTotal.value = res.data.data
}
getTotality()

// 表格数据读取
const loading = ref(false)
const searching = ref(false)
let tableData = reactive<{ values: Array<riderData> | Array<adminData> | Array<bikeData> | Array<noParkingZoneData> | Array<recordData> }>({ values: [] })

if (props.tableConfig.api === '/riders') {
    tableData.values = reactive<Array<riderData>>([])
} else if (props.tableConfig.api === '/admins') {
    tableData.values = reactive<Array<adminData>>([])
} else if (props.tableConfig.api === '/bikes') {
    tableData.values = reactive<Array<bikeData>>([])
} else if (props.tableConfig.api === '/noParkingZones') {
    tableData.values = reactive<Array<noParkingZoneData>>([])
} else if (props.tableConfig.api === '/records') {
    tableData.values = reactive<Array<recordData>>([])
}

// 搜索框数据
const searchInput = ref('')
const searchColumn = ref('')
// 排序数据
const sortField = ref('')
const sortOrder = ref('')

const getTableData = async (moreurl?: string) => {
    loading.value = true
    let res;
    if (moreurl) {
        res = await http.getList(
            props.tableConfig.api + moreurl,
            userStore.token,
            pagination.value.currentPage,
            pagination.value.pageSize,
            sortField.value,
            sortOrder.value,
            searchColumn.value,
            searchInput.value
        );
    } else {
        res = await http.getList(
            props.tableConfig.api,
            userStore.token,
            pagination.value.currentPage,
            pagination.value.pageSize,
            sortField.value,
            sortOrder.value,
            searchColumn.value,
            searchInput.value
        );
    }

    if (Array.isArray(res.data.data)) {
        tableData.values = res.data.data.map((item: any) => ({
            ...item,
            onMap: false // Insert 'onMap' property with default value as false
        }) as riderData);
    } else {
        console.error('Unexpected response structure');
    }
    loading.value = false;
    console.log(tableData.values);
}
getTableData()

// 监听noParkingZoneStore中的isNoParkingZoneOperationFinish，如果变为true，重新获取数据
watch(() => mapStatusStore.isNoParkingZoneOperationFinish, (newValue, oldValue) => {
    console.log(newValue)
    getTableData()
    mapStatusStore.setNoParkingZoneOperationFinishStatus(false);
})

// 页大小改变
const handleSizeChange = async (val: number) => {
    pagination.value.pageSize = val
    if (searching.value) {
        getTableData("/search")
    } else {
        getTableData()
    }
}

// 当前页改变
const handleCurrentChange = async (val: number) => {
    pagination.value.currentPage = val
    if (searching.value) {
        getTableData("/search")
    } else {
        getTableData()
    }
}

// 搜索
const handleSearch = async () => {
    // 重置页码
    pagination.value.currentPage = 1
    // 重置总数
    const res = await http.getSelectCounts(props.tableConfig.api, userStore.token, searchColumn.value, searchInput.value)
    tableDataTotal.value = res.data.data
    // 设置针对搜索的排序字段
    sortOrder.value = 'asc'
    searching.value = true
    getTableData("/search")
}

const handleResetSearch = async () => {
    // 重置页码
    pagination.value.currentPage = 1
    // 重置总数
    getTotality()
    // 重置搜索框
    searchInput.value = ''
    searchColumn.value = ''
    searching.value = false
    getTableData()
}

// 排序改变
const handleSort = async (prop: any) => {
    sortOrder.value = prop.order === 'ascending' ? 'asc' : (prop.order === 'descending' ? 'desc' : '');
    sortField.value = prop.prop;
    getTableData()
}


// 根据prop，得到第一列的属性名
const getFirstColumnProp = () => {
    return props.tableConfig.columns[0].prop
}


// 编辑
let formData = reactive({});
const handleEdit = (row) => {
    // 打印EditForm传入的参数
    statusStore.setEditFinish(false)
    if (props.tableConfig.api == '/noParkingZones') {
        noParkingZoneStore.editingNoParkingZone = row.zoneid
        mapStatusStore.setForNoParkingZoneEditStatus(true)
    } else {
        formData = row;
        dialogFormVisible.value = true
        console.log(props.tableConfig)
    }
}


watch(() => statusStore.isEditFinish, (newValue, oldValue) => {
    if (newValue) {
        dialogFormVisible.value = false
        getTableData()
        statusStore.setEditFinish(false)
    }
})

watch(() => statusStore.isCreateFinish, (newValue, oldValue) => {
    if (newValue) {
        getTableData()
        statusStore.setCreateFinish(false)
    }
})


// 删除
const handleDelete = (id: number) => {
    // 删除操作
    console.log(id)
    statusStore.isEditFinish = false
    http.delete(props.tableConfig.api, userStore.token, id)
    getTableData()
    statusStore.isEditFinish = true
    // 如果是禁停区，需要在地图上删除，通过store设置状态
    if (props.tableConfig.api == '/noParkingZones') {
        // 设置对应的deleteid
        noParkingZoneStore.deleteId = id
        mapStatusStore.setForNoParkingZoneDeleteStatus(true)
    }
}

// 在地图上查看
const handleMAP = (row) => {
    // 如果对应的url是/records在recordStore中存储当前行的数据
    if (props.tableConfig.api == '/rideRecords') {
        useRecordStore().addRecord(userStore.token ?? '', row.orderid)
    }
    if (props.tableConfig.api == '/noParkingZones') {
        useNoParkingZoneStore().addVisibleNoParkingZone(userStore.token ?? '', row.zoneid)
    }
}

// 取消地图查看
const handleNOMAP = (row) => {
    // 在recordStore中删除当前行的数据
    if (props.tableConfig.api == '/rideRecords') {
        useRecordStore().removeRecord(row.orderid)
    }
    if (props.tableConfig.api == '/noParkingZones') {
        useNoParkingZoneStore().removeVisibleNoParkingZone(row.zoneid)
    }
}

</script>

<style scoped>
.table {
    padding: 20px;
    background-color: azure;
}

.el-button {
    margin-right: 8px;
    /* Add right margin to the button */
    border: 1px solid #dcdfe6;
}

.input-with-select {
    max-width: 650px;
    /* Adjust the max-width as needed */
}
</style>