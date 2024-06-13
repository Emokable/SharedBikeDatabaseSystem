<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 01:29:32
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-13 20:24:53
-->
<template>
    <el-table :data="tableData.values" v-loading="loading" style="width: 80%" @sort-change = "handleSort">
        <el-table-column
            v-for="item in props.tableConfig.columns"
            :key="item.prop"
            :prop="item.prop"
            :label="item.label"
            :sortable= "'custom'"
            :fit= true
        >
            <!-- 表头自定义 -->
            <template #header="{ column }">
                <slot :name="item.prop + 'Header'">
                    <div class="inline-flex" :style="item.labelStyle">
                        <span>{{ item.label }}</span>
                        <el-tooltip
                        popper-class="table-tooltip"
                        effect="dark"
                        placement="top-start"
                        :content="item.tooltip"
                        v-if="item.tooltip"
                        >
                        </el-tooltip>
                    </div>
                </slot>
            </template>
            <!-- 单元格自定义 -->
            <!-- <template #default="{ row }">
                <slot :name="item.prop" :row="row">
                    <span>{{ item.formatter ? item.formatter(row) : row[item.prop] }}</span>
                </slot>
            </template> -->
        </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pagination.currentPage"
        :page-sizes="[10, 20, 40, 80]"
        :page-size="pagination.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableDataTotal">
    </el-pagination>
</template>

<script lang="ts" setup>
import { computed, ref, reactive } from 'vue'
import { http } from '../utils/http'
import { TableConfig } from '../types/table'
import { riderData } from '../types/rider'
import { useUserStore } from '../stores/user'
import { tr } from 'element-plus/es/locales.mjs'

const pagination = ref({
    currentPage: 1,
    pageSize: 20
})

const props = defineProps<{
    tableConfig: TableConfig
}>()
const userStore = useUserStore()

const tableDataTotal = ref<number>(0)

// 通过http的getTotality方法获取总数
const getTotality = async () => {
    const res = await http.getCount(props.tableConfig.api, userStore.token)
    tableDataTotal.value = res.data.data
}
getTotality()

// 读取表格数据
const loading = ref(false)
const tableData = reactive({
    values: [] as riderData[]
})
const getTableData = async () => {
    loading.value = true
    const res = await http.getList(props.tableConfig.api, userStore.token)
    if (Array.isArray(res.data.data)) {
        tableData.values = res.data.data.map((item: any) => item as riderData);
    } else {
        console.error('Unexpected response structure');
    }
    loading.value = false
    console.log(tableData.values)
}
getTableData()

const handleSizeChange = async (val: number) => {
    pagination.value.pageSize = val
    let currentPageData = await http.getList(props.tableConfig.api, userStore.token, pagination.value.currentPage, pagination.value.pageSize);
    tableData.values = currentPageData.data.data.map((item: any) => item as riderData);
}

const handleSort = async (prop: any ) => {
    const sortOrder = prop.order === 'ascending' ? 'asc' : (prop.order === 'descending' ? 'desc' : '');
    const sortField = prop.prop;
    try {
        let sortData = await http.getList(props.tableConfig.api, userStore.token, pagination.value.currentPage, pagination.value.pageSize, sortField, sortOrder);
        tableData.values = sortData.data.data.map((item: any) => item as riderData);
    } catch (error) {
        console.error('Failed to fetch sorted data:', error);
    }
}

const handleCurrentChange = async (val: number) => {
    pagination.value.currentPage = val
    let currentPageData = await http.getList(props.tableConfig.api, userStore.token, pagination.value.currentPage, pagination.value.pageSize);
    tableData.values = currentPageData.data.data.map((item: any) => item as riderData);
}

</script>