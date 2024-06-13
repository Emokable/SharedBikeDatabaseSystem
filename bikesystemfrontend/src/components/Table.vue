<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:29:32
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-14 01:02:46
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
                                <el-button :icon="Search" @click="handleSearch"/>
                            </template>
                        </el-input>
                    </div>
                </template>
                <template #default="scope">
                    <el-button size="small" @click="handleEdit(scope.row[getFirstColumnProp()])">
                        Edit
                    </el-button>
                    <el-button size="small" type="danger" @click="handleDelete(scope.row[getFirstColumnProp()])">
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
import { Search } from '@element-plus/icons-vue'

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
const tableData = reactive({
    values: [] as riderData[]
})
const getTableData = async () => {
    loading.value = true
    const res = await http.getList(props.tableConfig.api, userStore.token, pagination.value.currentPage, pagination.value.pageSize)
    if (Array.isArray(res.data.data)) {
        tableData.values = res.data.data.map((item: any) => item as riderData);
    } else {
        console.error('Unexpected response structure');
    }
    loading.value = false
    console.log(tableData.values)
}
getTableData()

// 页大小改变
const handleSizeChange = async (val: number) => {
    pagination.value.pageSize = val
    let currentPageData = await http.getList(props.tableConfig.api, userStore.token, pagination.value.currentPage, pagination.value.pageSize);
    tableData.values = currentPageData.data.data.map((item: any) => item as riderData);
}

// 排序改变
const handleSort = async (prop: any) => {
    const sortOrder = prop.order === 'ascending' ? 'asc' : (prop.order === 'descending' ? 'desc' : '');
    const sortField = prop.prop;
    try {
        let sortData = await http.getList(props.tableConfig.api, userStore.token, pagination.value.currentPage, pagination.value.pageSize, sortField, sortOrder);
        tableData.values = sortData.data.data.map((item: any) => item as riderData);
    } catch (error) {
        console.error('Failed to fetch sorted data:', error);
    }
}

// 当前页改变
const handleCurrentChange = async (val: number) => {
    pagination.value.currentPage = val
    let currentPageData = await http.getList(props.tableConfig.api, userStore.token, pagination.value.currentPage, pagination.value.pageSize);
    tableData.values = currentPageData.data.data.map((item: any) => item as riderData);
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

// 搜索
const searchInput = ref('')
const searchColumn = ref('')
const handleSearch = async () => {
    if (searchInput.value === '') {
        getTableData()
    } else {
        const res = await http.search(props.tableConfig.api, userStore.token, pagination.value.currentPage, pagination.value.pageSize, searchColumn.value, searchInput.value)
        tableData.values = res.data.data.map((item: any) => item as riderData);
    }
}

</script>

<style scoped>
.table {
    padding: 20px;
    background-color: azure;
}
</style>