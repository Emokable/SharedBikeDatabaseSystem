import { ref } from 'vue'
import { defineStore } from 'pinia'
import { recordData } from '../types/record'
import { http } from '../utils/http'
import { TableConfig } from '../types/table'

export const useRecordStore = defineStore('record', () => {
    const record = ref<recordData[]>([])
    
    // 获取对应的record记录,

    return { record }
})