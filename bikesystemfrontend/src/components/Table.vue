<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:29:32
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-14 10:58:28
-->
<template>
    <div class="table">
        <el-table :data="tableData.values" :table-layout="'auto'" v-loading="loading" :stripe="true" :height="800"
            style="width: 100%" @sort-change="handleSort">
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

            <el-table-column align="right">
                <template #header>
                    <div class="mt-4">
                        <el-input v-model="searchInput" style="max-width: 600px" placeholder="Please input"
                            class="input-with-select">
                            <template #prepend>
                                <el-select v-model="searchColumn" placeholder="选择查询属性" style="width: 115px">
                                    <el-option v-for="item in props.tableConfig.columns" :value="item.prop"
                                        :label="item.label" />
                                </el-select>
                            </template>
                            <template #append>
                                <el-button :icon="Search" type="primary" @click="handleSearch"
                                    :disabled="!searchColumn || !searchInput" />
                                <el-button :icon="CloseBold" type="warning" @click="handleResetSearch"
                                    :disabled="!searchColumn || !searchInput"></el-button>
                            </template>
                        </el-input>
                    </div>
                </template>
                <template #default="scope">
                    <el-button size="small" @click="handleEdit(scope.row[getFirstColumnProp()])">
                        Edit
                    </el-button>
                    <el-button size="small" type="danger" @click="handleDelete(scope.row[getFirstColumnProp()])"
                        v-if="props.tableConfig.api === '/admins' || props.tableConfig.api === '/noParkingZones'">
                        Delete
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="pagination.currentPage" :page-sizes="[20, 40, 60, 80]" :page-size="pagination.pageSize"
            layout="total, sizes, prev, pager, next, jumper" :total="tableDataTotal">
        </el-pagination>
    </div>
</template>

<script lang="ts" setup>
import { computed, ref, reactive } from 'vue'
import { http } from '../utils/http'
import { TableConfig } from '../types/table'
import { riderData } from '../types/rider'
import { useUserStore } from '../stores/user'
import { Search, CloseBold } from '@element-plus/icons-vue'
import { get } from 'http'

const pagination = ref({
    currentPage: 1,
    pageSize: 20
})

// 通过props获取表格配置, 通过store获取token
const props = defineProps<{
    tableConfig: TableConfig
}>()
const userStore = useUserStore()

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
const tableData = reactive({
    values: [] as riderData[]
})

// 搜索框数据
const searchInput = ref('')
const searchColumn = ref('')
// 排序数据
const sortField = ref('')
const sortOrder = ref('')

const getTableData = async (moreurl?: string) => {
    loading.value = true

    if (moreurl) {
        const res = await http.getList(
            props.tableConfig.api + moreurl,
            userStore.token,
            pagination.value.currentPage,
            pagination.value.pageSize,
            sortField.value,
            sortOrder.value,
            searchColumn.value,
            searchInput.value
        )
        if (Array.isArray(res.data.data)) {
            tableData.values = res.data.data.map((item: any) => item as riderData);
        } else {
            console.error('Unexpected response structure');
        }
    } else {
        const res = await http.getList(
            props.tableConfig.api,
            userStore.token,
            pagination.value.currentPage,
            pagination.value.pageSize,
            sortField.value,
            sortOrder.value,
            searchColumn.value,
            searchInput.value
        )
        if (Array.isArray(res.data.data)) {
            tableData.values = res.data.data.map((item: any) => item as riderData);
        } else {
            console.error('Unexpected response structure');
        }
    }
    loading.value = false
    console.log(tableData.values)
}
getTableData()

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
const handleEdit = (id: number) => {

}

// 删除
const handleDelete = (id: number) => {
    // 删除操作
    console.log(id)
    http.delete(props.tableConfig.api, userStore.token, id)
    getTableData()
}

</script>

<style scoped>
.table {
    padding: 20px;
    background-color: azure;
}
</style>