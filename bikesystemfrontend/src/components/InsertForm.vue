<!--
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 11:32:32
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 22:07:19
-->
<template>
    <el-form :model="props.formData" label-width="auto" style="max-width: 600px">
        <template v-for="column in props.tableConfig.columns" :key="column.prop">
            <el-form-item :label="column.label">
                <template v-if="column.isEnum">
                    <el-radio-group v-model="props.formData[column.prop]">
                        <el-radio v-for="option in column.enumOptions" :label="option" :key="option">{{ option
                            }}</el-radio>
                    </el-radio-group>
                </template>
                <template v-else-if="column.isTime">
                    <el-date-picker v-model="props.formData[column.prop]" type="datetime" placeholder="Select date and time"
                     value-format="YYYY-MM-DD HH:mm:ss" />
                </template>
                <template v-else-if="!column.prop.endsWith('id')">
                    <el-input v-model="props.formData[column.prop]" />
                </template>
                <template v-else>
                    <span>{{ props.formData[column.prop] }}</span>
                </template>
            </el-form-item>
        </template>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">编辑</el-button>
        </el-form-item>
    </el-form>
</template>

<script lang="ts" setup>
import { reactive, defineProps, ref } from 'vue'
import { TableConfig } from '../types/table'
import { http } from '../utils/http'
import { useUserStore } from '../stores/user'
import { useStatusStore } from '../stores/operationStatus'

const props = defineProps<{
    tableConfig: TableConfig
    formData: any
}>();

const userStore = useUserStore()
const statusStore = useStatusStore()
const idValue = ref(0);

const onSubmit = () => {
    console.log('submit!', props.formData)
    if (props.tableConfig.api === '/admins' && userStore.isSuperuser) {
        http.post(props.tableConfig.api, props.formData, userStore.token)
            .then(() => {
                statusStore.setCreateFinish(true)
            })
            .catch(() => {
                window.alert('插入失败')
            })
    } else {
        http.post(props.tableConfig.api, props.formData, userStore.token)
            .then(() => {
                statusStore.setCreateFinish(true)
            })
            .catch(() => {
                window.alert('插入失败')
            })
    }
}

</script>